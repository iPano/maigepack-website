<template>
  <div class="min-h-screen flex flex-col">
    <LayoutHeader />

    <main class="flex-1">
      <slot />
    </main>

    <LayoutFooter />
  </div>
</template>

<script setup lang="ts">
// Layout logic can be added here if needed
</script>

