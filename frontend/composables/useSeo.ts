/**
 * SEO composable — wraps useSeoMeta + adds canonical link tag.
 *
 * Usage:
 *   useSeo({
 *     title: 'Page Title | MagerPack',
 *     description: 'Page description ...',
 *     ogImage: '/images/og-image.jpg',
 *     path: '/products',          // optional, defaults to current route
 *   })
 */
export interface SeoOptions {
  title: string
  description: string
  ogImage?: string
  path?: string        // canonical path override (e.g. '/products/rigid-boxes')
  noindex?: boolean    // set true for admin / private pages
}

export function useSeo(options: SeoOptions) {
  const config = useRuntimeConfig()
  const route = useRoute()

  const siteUrl = (config.public.siteUrl as string) || 'https://www.magerpack.com'
  const canonicalPath = options.path ?? route.path
  const canonicalUrl = `${siteUrl}${canonicalPath}`

  useSeoMeta({
    title: options.title,
    ogTitle: options.title,
    description: options.description,
    ogDescription: options.description,
    ogImage: options.ogImage ?? '/images/og-image.jpg',
    ogUrl: canonicalUrl,
    twitterCard: 'summary_large_image',
    robots: options.noindex ? 'noindex, nofollow' : 'index, follow'
  })

  useHead({
    link: [
      { rel: 'canonical', href: canonicalUrl }
    ]
  })
}
