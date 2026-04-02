export default defineNuxtConfig({
  ssr: false, // Temporarily disable SSR to avoid Nitro build issues on Vercel
  app: {
    head: {
      title: 'Custom Packaging Solutions | MaigePack Manufacturing',
      meta: [
        { name: 'description', content: 'Professional custom packaging manufacturer specializing in rigid boxes, folding cartons, and display solutions for electronics, cosmetics, and luxury products.' },
        { name: 'keywords', content: 'custom packaging, rigid boxes, folding boxes, display packaging, manufacturing, B2B' }
      ],
      htmlAttrs: { lang: 'en' }
    }
  },
  modules: [
    '@nuxtjs/tailwindcss'
  ],
  css: ['~/assets/css/main.css'],
  runtimeConfig: {
    public: {
      apiBaseUrl: process.env.NUXT_PUBLIC_API_BASE_URL || 'http://localhost:8081'
    }
  }
})

