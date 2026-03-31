<template>
  <header class="bg-white shadow-sm border-b border-gray-200 sticky top-0 z-50">
    <div class="container-custom">
      <div class="flex justify-between items-center py-4">
        <!-- Logo -->
        <div class="flex items-center">
          <NuxtLink to="/" class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-gradient-primary rounded-lg flex items-center justify-center">
              <span class="text-white font-bold text-lg">M</span>
            </div>
            <div class="hidden md:block">
              <h1 class="text-xl font-bold text-navy-900">MaigePack</h1>
              <p class="text-xs text-gray-600">Custom Packaging Solutions</p>
            </div>
          </NuxtLink>
        </div>

        <!-- Desktop Navigation -->
        <nav class="hidden lg:flex items-center space-x-8">
          <NuxtLink
            to="/"
            class="text-navy-700 hover:text-primary-600 font-medium transition-colors duration-200"
          >
            Home
          </NuxtLink>

          <div class="relative group">
            <button class="text-navy-700 hover:text-primary-600 font-medium transition-colors duration-200 flex items-center">
              Products
              <svg class="ml-1 h-4 w-4 transition-transform group-hover:rotate-180" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
              </svg>
            </button>
            <div class="absolute top-full left-0 mt-2 w-64 bg-white rounded-lg shadow-large border border-gray-200 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-200">
              <div class="p-2">
                <NuxtLink to="/products/rigid-boxes" class="block px-4 py-3 text-sm text-navy-700 hover:bg-gray-50 rounded-md transition-colors">
                  Rigid Boxes
                </NuxtLink>
                <NuxtLink to="/products/folding-boxes" class="block px-4 py-3 text-sm text-navy-700 hover:bg-gray-50 rounded-md transition-colors">
                  Folding Boxes
                </NuxtLink>
                <NuxtLink to="/products/display-boxes" class="block px-4 py-3 text-sm text-navy-700 hover:bg-gray-50 rounded-md transition-colors">
                  Display Boxes
                </NuxtLink>
                <NuxtLink to="/products/packaging-sleeves" class="block px-4 py-3 text-sm text-navy-700 hover:bg-gray-50 rounded-md transition-colors">
                  Packaging Sleeves
                </NuxtLink>
                <NuxtLink to="/products/custom-inserts" class="block px-4 py-3 text-sm text-navy-700 hover:bg-gray-50 rounded-md transition-colors">
                  Custom Inserts
                </NuxtLink>
              </div>
            </div>
          </div>

          <div class="relative group">
            <button class="text-navy-700 hover:text-primary-600 font-medium transition-colors duration-200 flex items-center">
              Industries
              <svg class="ml-1 h-4 w-4 transition-transform group-hover:rotate-180" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
              </svg>
            </button>
            <div class="absolute top-full left-0 mt-2 w-64 bg-white rounded-lg shadow-large border border-gray-200 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-200">
              <div class="p-2">
                <NuxtLink to="/industries/electronics-packaging" class="block px-4 py-3 text-sm text-navy-700 hover:bg-gray-50 rounded-md transition-colors">
                  Electronics Packaging
                </NuxtLink>
                <NuxtLink to="/industries/cosmetics-packaging" class="block px-4 py-3 text-sm text-navy-700 hover:bg-gray-50 rounded-md transition-colors">
                  Cosmetics Packaging
                </NuxtLink>
                <NuxtLink to="/industries/jewelry-packaging" class="block px-4 py-3 text-sm text-navy-700 hover:bg-gray-50 rounded-md transition-colors">
                  Jewelry Packaging
                </NuxtLink>
                <NuxtLink to="/industries/fragrance-packaging" class="block px-4 py-3 text-sm text-navy-700 hover:bg-gray-50 rounded-md transition-colors">
                  Fragrance Packaging
                </NuxtLink>
              </div>
            </div>
          </div>

          <NuxtLink
            to="/about"
            class="text-navy-700 hover:text-primary-600 font-medium transition-colors duration-200"
          >
            About Us
          </NuxtLink>

          <NuxtLink
            to="/contact-us"
            class="text-navy-700 hover:text-primary-600 font-medium transition-colors duration-200"
          >
            Contact
          </NuxtLink>
        </nav>

        <!-- CTA Button -->
        <div class="hidden lg:flex items-center space-x-4">
          <Button variant="primary" to="/contact-us">
            Get Free Quote
          </Button>
        </div>

        <!-- Mobile Menu Button -->
        <button
          @click="toggleMobileMenu"
          class="lg:hidden flex items-center justify-center w-10 h-10 text-navy-700 hover:text-primary-600 transition-colors"
        >
          <svg class="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path v-if="!mobileMenuOpen" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
            <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      </div>
    </div>

    <!-- Mobile Menu -->
    <div v-show="mobileMenuOpen" class="lg:hidden bg-white border-t border-gray-200">
      <div class="container-custom py-4">
        <nav class="space-y-2">
          <NuxtLink
            to="/"
            class="block px-4 py-3 text-navy-700 hover:bg-gray-50 rounded-md transition-colors"
            @click="closeMobileMenu"
          >
            Home
          </NuxtLink>

          <div>
            <button
              @click="toggleProductsMenu"
              class="flex items-center justify-between w-full px-4 py-3 text-navy-700 hover:bg-gray-50 rounded-md transition-colors"
            >
              Products
              <svg :class="{ 'rotate-180': productsMenuOpen }" class="h-4 w-4 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
              </svg>
            </button>
            <div v-show="productsMenuOpen" class="pl-4 space-y-1">
              <NuxtLink
                to="/products/rigid-boxes"
                class="block px-4 py-2 text-sm text-navy-600 hover:bg-gray-50 rounded-md transition-colors"
                @click="closeMobileMenu"
              >
                Rigid Boxes
              </NuxtLink>
              <NuxtLink
                to="/products/folding-boxes"
                class="block px-4 py-2 text-sm text-navy-600 hover:bg-gray-50 rounded-md transition-colors"
                @click="closeMobileMenu"
              >
                Folding Boxes
              </NuxtLink>
              <NuxtLink
                to="/products/display-boxes"
                class="block px-4 py-2 text-sm text-navy-600 hover:bg-gray-50 rounded-md transition-colors"
                @click="closeMobileMenu"
              >
                Display Boxes
              </NuxtLink>
              <NuxtLink
                to="/products/packaging-sleeves"
                class="block px-4 py-2 text-sm text-navy-600 hover:bg-gray-50 rounded-md transition-colors"
                @click="closeMobileMenu"
              >
                Packaging Sleeves
              </NuxtLink>
              <NuxtLink
                to="/products/custom-inserts"
                class="block px-4 py-2 text-sm text-navy-600 hover:bg-gray-50 rounded-md transition-colors"
                @click="closeMobileMenu"
              >
                Custom Inserts
              </NuxtLink>
            </div>
          </div>

          <div>
            <button
              @click="toggleIndustriesMenu"
              class="flex items-center justify-between w-full px-4 py-3 text-navy-700 hover:bg-gray-50 rounded-md transition-colors"
            >
              Industries
              <svg :class="{ 'rotate-180': industriesMenuOpen }" class="h-4 w-4 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
              </svg>
            </button>
            <div v-show="industriesMenuOpen" class="pl-4 space-y-1">
              <NuxtLink
                to="/industries/electronics-packaging"
                class="block px-4 py-2 text-sm text-navy-600 hover:bg-gray-50 rounded-md transition-colors"
                @click="closeMobileMenu"
              >
                Electronics Packaging
              </NuxtLink>
              <NuxtLink
                to="/industries/cosmetics-packaging"
                class="block px-4 py-2 text-sm text-navy-600 hover:bg-gray-50 rounded-md transition-colors"
                @click="closeMobileMenu"
              >
                Cosmetics Packaging
              </NuxtLink>
              <NuxtLink
                to="/industries/jewelry-packaging"
                class="block px-4 py-2 text-sm text-navy-600 hover:bg-gray-50 rounded-md transition-colors"
                @click="closeMobileMenu"
              >
                Jewelry Packaging
              </NuxtLink>
              <NuxtLink
                to="/industries/fragrance-packaging"
                class="block px-4 py-2 text-sm text-navy-600 hover:bg-gray-50 rounded-md transition-colors"
                @click="closeMobileMenu"
              >
                Fragrance Packaging
              </NuxtLink>
            </div>
          </div>

          <NuxtLink
            to="/about"
            class="block px-4 py-3 text-navy-700 hover:bg-gray-50 rounded-md transition-colors"
            @click="closeMobileMenu"
          >
            About Us
          </NuxtLink>

          <NuxtLink
            to="/contact-us"
            class="block px-4 py-3 text-navy-700 hover:bg-gray-50 rounded-md transition-colors"
            @click="closeMobileMenu"
          >
            Contact
          </NuxtLink>

          <div class="pt-4">
            <Button variant="primary" full-width to="/contact-us" @click="closeMobileMenu">
              Get Free Quote
            </Button>
          </div>
        </nav>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
const mobileMenuOpen = ref(false)
const productsMenuOpen = ref(false)
const industriesMenuOpen = ref(false)

const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value
  if (!mobileMenuOpen.value) {
    productsMenuOpen.value = false
    industriesMenuOpen.value = false
  }
}

const closeMobileMenu = () => {
  mobileMenuOpen.value = false
  productsMenuOpen.value = false
  industriesMenuOpen.value = false
}

const toggleProductsMenu = () => {
  productsMenuOpen.value = !productsMenuOpen.value
  industriesMenuOpen.value = false
}

const toggleIndustriesMenu = () => {
  industriesMenuOpen.value = !industriesMenuOpen.value
  productsMenuOpen.value = false
}

// Close mobile menu on route change
watch(() => useRoute().path, () => {
  closeMobileMenu()
})
</script>