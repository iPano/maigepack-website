export default defineNuxtConfig({
  ssr: true,
  components: [
    // ui/ components registered WITHOUT prefix: <Button>, <Card>, <Badge>, <Input>
    { path: '~/components/ui', pathPrefix: false },
    // All other components keep Nuxt's default prefix behaviour:
    // layout/Header.vue → <LayoutHeader>, sections/Hero.vue → <SectionsHero>, etc.
    { path: '~/components', pathPrefix: true }
  ],
  app: {
    head: {
      title: 'Custom Packaging Solutions | MagerPack Manufacturing',
      meta: [
        { name: 'description', content: 'Professional custom packaging manufacturer specializing in rigid boxes, folding cartons, and display solutions for electronics, cosmetics, and luxury products.' },
        { name: 'keywords', content: 'custom packaging, rigid boxes, folding boxes, display packaging, manufacturing, B2B' }
      ],
      htmlAttrs: { lang: 'en' }
    }
  },
  modules: [
    '@nuxtjs/tailwindcss',
    '@nuxtjs/sitemap'
  ],
  site: {
    url: process.env.NUXT_PUBLIC_SITE_URL || 'https://magerpack-website.vercel.app',
    name: 'MagerPack Manufacturing'
  },
  sitemap: {
    strictNuxtContentPaths: false,
    urls: [
      '/',
      '/products',
      '/industries',
      '/contact-us',
      '/artwork-guideline'
    ]
  },
  css: ['~/assets/css/main.css'],
  runtimeConfig: {
    public: {
      apiBaseUrl: process.env.NUXT_PUBLIC_API_BASE_URL || 'http://localhost:8081',
      siteUrl: process.env.NUXT_PUBLIC_SITE_URL || 'https://magerpack-website.vercel.app'
    }
  }
})

