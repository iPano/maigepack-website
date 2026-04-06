<template>
  <div />
</template>

<script setup lang="ts">
definePageMeta({ middleware: 'admin-auth', layout: 'admin' })

await navigateTo('/admin/products', { replace: true })
</script>
