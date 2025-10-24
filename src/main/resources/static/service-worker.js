/* v1.0.0 — basic PWA shell */
const CACHE_PREFIX = 'assets-app';
const APP_VERSION = 'v1.0.0';
const RUNTIME_CACHE = `${CACHE_PREFIX}-runtime-${APP_VERSION}`;
const STATIC_CACHE = `${CACHE_PREFIX}-static-${APP_VERSION}`;

//  that truly exist under src/main/resources/static
const STATIC_ASSETS = [
  '/offline.html',
  '/manifest.json',
  // '/css/main.css',
  // '/js/app.js',
];

// Utility: identify HTML navigations
const isNavigationRequest = (req) =>
  req.mode === 'navigate' ||
  (req.method === 'GET' && req.headers.get('accept')?.includes('text/html'));

// Install: pre-cache minimal shell
self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(STATIC_CACHE).then((cache) => cache.addAll(STATIC_ASSETS))
  );
  self.skipWaiting();
});

// Activate: cleanup old caches
self.addEventListener('activate', (event) => {
  event.waitUntil(
    caches.keys().then((keys) =>
      Promise.all(
        keys
          .filter((k) => k.startsWith(CACHE_PREFIX) && !k.endsWith(APP_VERSION))
          .map((k) => caches.delete(k))
      )
    )
  );
  self.clients.claim();
});

// Fetch strategy:
// - HTML: network-first (fallback to cache, then offline.html)
// - Static assets: cache-first
// - Skip API calls (let them hit the network)
self.addEventListener('fetch', (event) => {
  const { request } = event;
  const url = new URL(request.url);

  // Bypass non-GET and third-party
  if (request.method !== 'GET' || url.origin !== self.location.origin) return;

  // Don’t intercept API calls (adjust path if your APIs differ)
  if (url.pathname.startsWith('/api/')) return;

  // HTML pages — network-first
  if (isNavigationRequest(request)) {
    event.respondWith(
      fetch(request)
        .then((res) => {
          // cache a copy of successful navigations
          const resClone = res.clone();
          caches.open(RUNTIME_CACHE).then((cache) => cache.put(request, resClone));
          return res;
        })
        .catch(async () => {
          // try runtime cache, then dedicated offline page
          const cache = await caches.open(RUNTIME_CACHE);
          const cached = await cache.match(request);
          return cached || caches.match('/offline.html');
        })
    );
    return;
  }

  // Static assets — cache-first
  event.respondWith(
    caches.match(request).then((cached) => {
      if (cached) return cached;
      return fetch(request)
        .then((res) => {
          // cache successful same-origin assets
          const resClone = res.clone();
          caches.open(RUNTIME_CACHE).then((cache) => cache.put(request, resClone));
          return res;
        })
        .catch(() => {
          // optional: return nothing on asset failure
        });
    })
  );
});
