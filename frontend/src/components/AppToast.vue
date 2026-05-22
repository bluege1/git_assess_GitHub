<script setup lang="ts">
import { useToast } from '../composables/useToast'

const { visible, message, type } = useToast()
</script>

<template>
  <Transition name="toast">
    <div v-if="visible" class="toast" :class="type" role="status" aria-live="polite">
      {{ message }}
    </div>
  </Transition>
</template>

<style scoped>
.toast {
  position: fixed;
  top: 24px;
  left: 50%;
  z-index: 9999;
  transform: translateX(-50%);
  max-width: min(90vw, 420px);
  padding: 12px 20px;
  border-radius: 8px;
  font-size: 0.9375rem;
  font-family: system-ui, sans-serif;
  box-shadow: 0 4px 12px rgb(0 0 0 / 0.12);
}

.toast.success {
  color: #15803d;
  background: #f0fdf4;
  border: 1px solid #86efac;
}

.toast.error {
  color: #b91c1c;
  background: #fef2f2;
  border: 1px solid #fca5a5;
}

.toast-enter-active,
.toast-leave-active {
  transition:
    opacity 0.2s ease,
    transform 0.2s ease;
}

.toast-enter-from,
.toast-leave-to {
  opacity: 0;
  transform: translateX(-50%) translateY(-8px);
}
</style>
