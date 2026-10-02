// Protection-Tele Global Service Worker (Edge Caching & Global CDN)
const CACHE_NAME = 'protection-tele-cache-v2.5.0';
const URLS_TO_CACHE = [
  './',
  './index.html',
  './manifest.json',
  './store_icon.jpg',
  'https://fonts.googleapis.com/css2?family=Cairo:wght@400;600;700;800;900&display=swap'
];

self.addEventListener('install', event => {
  event.waitUntil(
    caches.open(CACHE_NAME).then(cache => {
      console.log('Protection-Tele: Global Edge Cache Installed');
      return cache.addAll(URLS_TO_CACHE);
    })
  );
  self.skipWaiting();
});

self.addEventListener('activate', event => {
  event.waitUntil(
    caches.keys().then(cacheNames => {
      return Promise.all(
        cacheNames.map(name => {
          if (name !== CACHE_NAME) {
            console.log('Protection-Tele: Purging old cache', name);
            return caches.delete(name);
          }
        })
      );
    })
  );
  self.clients.claim();
});

self.addEventListener('fetch', event => {
  event.respondWith(
    caches.match(event.request).then(response => {
      return response || fetch(event.request).then(networkResponse => {
        return networkResponse;
      });
    }).catch(() => caches.match('./index.html'))
  );
});
