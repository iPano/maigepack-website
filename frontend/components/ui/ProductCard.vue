<template>
  <div
    class="group bg-white rounded-xl overflow-hidden border border-gray-100 shadow-sm hover:shadow-lg transition-all duration-300 cursor-pointer flex flex-col"
    @click="$router.push(`/products/${product.slug}`)"
  >
    <!-- Image Container -->
    <div class="relative bg-gray-50 overflow-hidden" style="height: 220px;">
      <img
        v-if="product.imageUrl"
        :src="product.imageUrl"
        :alt="product.name"
        class="w-full h-full object-contain group-hover:scale-105 transition-transform duration-300 p-2"
      >
      <div v-else class="w-full h-full flex items-center justify-center bg-gradient-to-br from-primary-500 to-primary-700">
        <svg class="w-16 h-16 text-white opacity-60" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
        </svg>
      </div>

      <!-- Category Badge -->
      <span
        v-if="product.category"
        class="absolute top-3 left-3 bg-primary-600 text-white text-xs font-medium px-2.5 py-1 rounded-full"
      >
        {{ product.category }}
      </span>
    </div>

    <!-- Card Body -->
    <div class="flex flex-col flex-1 p-5 space-y-3">
      <!-- Product Name -->
      <h3 class="text-base font-semibold text-navy-900 group-hover:text-primary-600 transition-colors line-clamp-2 leading-snug">
        {{ product.name }}
      </h3>

      <!-- Short Description -->
      <p v-if="product.shortDescription" class="text-sm text-gray-500 line-clamp-3 leading-relaxed">
        {{ product.shortDescription }}
      </p>

      <!-- Key Specs -->
      <div class="space-y-1.5 text-xs text-gray-500">
        <div v-if="product.minimumOrderQuantity" class="flex items-center gap-1.5">
          <svg class="w-3.5 h-3.5 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 12l3-3 3 3 4-4M8 21l4-4 4 4M3 4h18"/>
          </svg>
          <span>Min Order: <strong class="text-gray-700">{{ product.minimumOrderQuantity }} pcs</strong></span>
        </div>
        <div v-if="product.leadTime" class="flex items-center gap-1.5">
          <svg class="w-3.5 h-3.5 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
          </svg>
          <span>Lead Time: <strong class="text-gray-700">{{ product.leadTime }}</strong></span>
        </div>
      </div>

      <!-- Industry Tags -->
      <div v-if="product.targetIndustries && product.targetIndustries.length > 0" class="flex flex-wrap gap-1">
        <span
          v-for="industry in product.targetIndustries.slice(0, 2)"
          :key="industry"
          class="text-xs bg-gray-100 text-gray-600 px-2 py-0.5 rounded-full"
        >
          {{ industry }}
        </span>
        <span v-if="product.targetIndustries.length > 2" class="text-xs text-gray-400">
          +{{ product.targetIndustries.length - 2 }} more
        </span>
      </div>

      <!-- Spacer -->
      <div class="flex-1" />

      <!-- CTAs -->
      <div class="flex gap-2 pt-2">
        <NuxtLink
          to="/contact-us"
          class="flex-1 text-center text-xs font-semibold bg-orange-500 hover:bg-orange-600 text-white px-3 py-2 rounded-lg transition-colors"
          @click.stop
        >
          Get Quote
        </NuxtLink>
        <button
          class="flex-1 text-xs font-semibold border border-primary-600 text-primary-600 hover:bg-primary-50 px-3 py-2 rounded-lg transition-colors"
        >
          View Details
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  product: {
    id: number
    name: string
    slug: string
    category?: string
    imageUrl?: string | null
    shortDescription?: string | null
    minimumOrderQuantity?: number | null
    leadTime?: string | null
    targetIndustries?: string[] | null
  }
}>()
</script>
