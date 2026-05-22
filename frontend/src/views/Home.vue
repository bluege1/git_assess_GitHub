<script setup lang="ts">
import { ref, onMounted } from 'vue'

interface HealthResponse {
  status: string
  message: string
  service: string
}

const loading = ref(false)
const error = ref('')
const data = ref<HealthResponse | null>(null)

async function fetchHealth() {
  loading.value = true
  error.value = ''
  data.value = null

  try {
    const res = await fetch('/api/health')
    if (!res.ok) {
      throw new Error(`HTTP ${res.status}`)
    }
    data.value = await res.json()
  } catch (e) {
    error.value = e instanceof Error ? e.message : '请求失败'
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchHealth()
})
</script>

<template>
  <div class="page">
    <h1>系统运行能力考核验证</h1>
    <p class="subtitle">Vue3 + Vite + TypeScript ↔ Spring Boot</p>

    <button :disabled="loading" @click="fetchHealth">
      {{ loading ? '请求中...' : '重新检测联调' }}
    </button>

    <div v-if="error" class="card error">
      <strong>联调失败</strong>
      <p>{{ error }}</p>
      <p class="hint">请确认后端已启动：mvnw.cmd spring-boot:run</p>
    </div>

    <div v-else-if="data" class="card success">
      <strong>联调成功</strong>
      <ul>
        <li><span>status</span> {{ data.status }}</li>
        <li><span>message</span> {{ data.message }}</li>
        <li><span>service</span> {{ data.service }}</li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.page {
  max-width: 640px;
  margin: 48px auto;
  padding: 0 24px;
  font-family: system-ui, sans-serif;
}

h1 {
  font-size: 1.5rem;
  margin-bottom: 8px;
}

.subtitle {
  color: #666;
  margin-bottom: 24px;
}

button {
  padding: 8px 16px;
  cursor: pointer;
  border: 1px solid #ccc;
  border-radius: 6px;
  background: #fff;
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.card {
  margin-top: 24px;
  padding: 16px;
  border-radius: 8px;
  border: 1px solid #ddd;
}

.card.success {
  border-color: #86efac;
  background: #f0fdf4;
}

.card.error {
  border-color: #fca5a5;
  background: #fef2f2;
}

ul {
  list-style: none;
  padding: 0;
  margin: 12px 0 0;
}

li {
  margin: 6px 0;
}

li span {
  display: inline-block;
  width: 72px;
  color: #666;
}

.hint {
  font-size: 0.875rem;
  color: #666;
  margin-top: 8px;
}
</style>
