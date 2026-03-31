<template>
  <span :class="badgeClasses">
    <slot />
  </span>
</template>

<script setup lang="ts">
interface Props {
  variant?: 'primary' | 'secondary' | 'success' | 'warning' | 'danger' | 'info'
  size?: 'sm' | 'md' | 'lg'
  rounded?: boolean
  outlined?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  variant: 'primary',
  size: 'md',
  rounded: false,
  outlined: false
})

const badgeClasses = computed(() => {
  const baseClasses = 'inline-flex items-center font-medium'

  const sizeClasses = {
    sm: 'px-2 py-1 text-xs',
    md: 'px-3 py-1 text-sm',
    lg: 'px-4 py-2 text-base'
  }

  const roundedClasses = props.rounded ? 'rounded-full' : 'rounded'

  const variantClasses = props.outlined
    ? {
        primary: 'text-primary-700 bg-primary-50 border border-primary-200',
        secondary: 'text-navy-700 bg-navy-50 border border-navy-200',
        success: 'text-success-700 bg-success-50 border border-success-200',
        warning: 'text-yellow-700 bg-yellow-50 border border-yellow-200',
        danger: 'text-red-700 bg-red-50 border border-red-200',
        info: 'text-blue-700 bg-blue-50 border border-blue-200'
      }
    : {
        primary: 'text-white bg-primary-600',
        secondary: 'text-white bg-navy-600',
        success: 'text-white bg-success-600',
        warning: 'text-yellow-800 bg-yellow-500',
        danger: 'text-white bg-red-600',
        info: 'text-white bg-blue-600'
      }

  return [
    baseClasses,
    sizeClasses[props.size],
    roundedClasses,
    variantClasses[props.variant]
  ].filter(Boolean).join(' ')
})
</script>