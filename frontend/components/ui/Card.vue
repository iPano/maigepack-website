<template>
  <div :class="cardClasses">
    <div v-if="$slots.header || title" class="card-header">
      <slot name="header">
        <h3 v-if="title" class="text-lg font-semibold text-navy-800">
          {{ title }}
        </h3>
      </slot>
    </div>

    <div v-if="$slots.image" class="card-image">
      <slot name="image" />
    </div>

    <div :class="contentClasses">
      <slot />
    </div>

    <div v-if="$slots.footer" class="card-footer">
      <slot name="footer" />
    </div>
  </div>
</template>

<script setup lang="ts">
interface Props {
  variant?: 'default' | 'bordered' | 'elevated' | 'flat'
  hover?: boolean
  padding?: 'none' | 'sm' | 'md' | 'lg'
  title?: string
}

const props = withDefaults(defineProps<Props>(), {
  variant: 'default',
  hover: false,
  padding: 'md'
})

const cardClasses = computed(() => {
  const baseClasses = 'bg-white rounded-xl overflow-hidden transition-all duration-300'

  const variantClasses = {
    default: 'border border-gray-200 shadow-soft',
    bordered: 'border-2 border-gray-200',
    elevated: 'shadow-medium',
    flat: 'border border-gray-100'
  }

  const hoverClasses = props.hover ? 'hover:shadow-large hover:border-primary-200 hover:-translate-y-1 cursor-pointer' : ''

  return [
    baseClasses,
    variantClasses[props.variant],
    hoverClasses
  ].filter(Boolean).join(' ')
})

const contentClasses = computed(() => {
  const paddingClasses = {
    none: '',
    sm: 'p-4',
    md: 'p-6',
    lg: 'p-8'
  }

  return paddingClasses[props.padding]
})
</script>

<style scoped>
.card-header {
  @apply px-6 py-4 border-b border-gray-100 bg-gray-50;
}

.card-image {
  @apply relative;
}

.card-footer {
  @apply px-6 py-4 border-t border-gray-100 bg-gray-50;
}
</style>