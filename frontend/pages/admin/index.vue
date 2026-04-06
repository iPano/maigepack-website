<template>
  <div />
</template>

<script setup lang="ts">
definePageMeta({ middleware: 'admin-auth' })

await navigateTo('/admin/products', { replace: true })
</script>
