<template>
  <div>
    <!-- Hero Section -->
    <SectionsHero />

    <!-- Features Section -->
    <SectionsFeatures />

    <!-- Products Preview Section -->
    <section class="section">
      <div class="container-custom">
        <div class="text-center mb-16">
          <h2 class="text-3xl md:text-4xl lg:text-5xl font-bold text-navy-900 mb-6">
            Our Product Categories
          </h2>
          <p class="text-lead max-w-3xl mx-auto">
            From premium rigid boxes to cost-effective folding solutions, we offer a complete range
            of packaging products to meet your specific needs.
          </p>
        </div>

        <div v-if="pending" class="flex justify-center py-12">
          <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
        </div>

        <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <Card
            v-for="category in categories"
            :key="category"
            hover
            padding="lg"
            class="text-center"
          >
            <div class="w-16 h-16 bg-gradient-primary rounded-full flex items-center justify-center mx-auto mb-6">
              <svg class="w-8 h-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-navy-800 mb-4">{{ category }}</h3>
            <p class="text-gray-600 mb-6">
              Professional packaging solutions tailored to your industry needs.
            </p>
            <Button variant="secondary" size="sm" :to="`/products/${category.toLowerCase().replace(/\s+/g, '-')}`">
              Learn More
            </Button>
          </Card>
        </div>

        <div v-if="categories.length === 0 && !pending" class="text-center py-12">
          <div class="text-gray-500">
            <svg class="w-16 h-16 mx-auto mb-4 opacity-50" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
            </svg>
            <p class="text-lg">Product categories coming soon...</p>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="section bg-primary-600 text-white">
      <div class="container-custom text-center">
        <h2 class="text-3xl md:text-4xl lg:text-5xl font-bold mb-6">
          Ready to Start Your Project?
        </h2>
        <p class="text-xl md:text-2xl mb-8 opacity-90 max-w-3xl mx-auto">
          Get expert packaging solutions with factory-direct pricing and rapid sampling support.
          Our team is ready to bring your vision to life.
        </p>
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
          <Button variant="secondary" size="lg" to="/contact-us">
            Get Free Quote in 24 Hours
          </Button>
          <Button variant="ghost" size="lg" to="/products">
            View All Products
          </Button>
        </div>

        <div class="mt-12 pt-8 border-t border-primary-500 border-opacity-30">
          <p class="text-primary-100">
            <strong>✓ 20+ Years Experience</strong> &nbsp;•&nbsp;
            <strong>✓ Factory Direct Pricing</strong> &nbsp;•&nbsp;
            <strong>✓ Global Shipping</strong>
          </p>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'

// SEO Meta Tags
useSeoMeta({
  title: 'Custom Packaging Solutions | MagerPack Manufacturing',
  ogTitle: 'Custom Packaging Solutions | MagerPack Manufacturing',
  description: 'Professional custom packaging manufacturer specializing in rigid boxes, folding cartons, and display solutions for electronics, cosmetics, and luxury products.',
  ogDescription: 'Professional custom packaging manufacturer specializing in rigid boxes, folding cartons, and display solutions for electronics, cosmetics, and luxury products.',
  ogImage: '/images/og-image.jpg',
  twitterCard: 'summary_large_image'
})

const config = useRuntimeConfig()
const apiBaseUrl = config.public.apiBaseUrl as string

// Use plain ref + onMounted so the fetch runs AFTER the page mounts.
// useFetch/useAsyncData always triggers Suspense in Nuxt 3 — if the backend
// is unreachable the Suspense error boundary catches it and kills the entire
// page mount, leaving Vue Router without event handlers (buttons unresponsive).
const categories = ref<string[]>([])
const pending = ref(true)

onMounted(async () => {
  try {
    const data = await $fetch<{ categories: string[] }>(`${apiBaseUrl}/api/public/site/nav`)
    categories.value = data?.categories ?? []
  } catch {
    // Backend unavailable — silently show empty categories, page still works
    categories.value = []
  } finally {
    pending.value = false
  }
})
</script>
