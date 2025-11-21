<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import axiosInstance from '@/utils/axiosInstance'
import api from '@/utils/api'
import type { APIRespone } from '@/types'

interface LoginForm {
  username: string
  password: string
}

const form = ref<LoginForm>({
  username: 'admin',
  password: 'admin'
})

const loading = ref(false)
const error = ref<string | null>(null)

const emit = defineEmits<{
  loginSuccess: [token: string]
}>()

const handleSubmit = async () => {
  error.value = null

  if (!form.value.username || !form.value.password) {
    error.value = 'Veuillez remplir tous les champs'
    return
  }

  loading.value = true

  try {
    const response = await axiosInstance.post(api('/api/auth/login'), {
      username: form.value.username,
      password: form.value.password
    })

    const responseData = response.data as APIRespone<{ token: string }>

    if (responseData.status !== 'success') {
      error.value = responseData.error?.message || 'Erreur lors de la connexion'
      return
    }

    const token = responseData.data!.token

    // Store token in localStorage
    localStorage.setItem('authToken', token)

    // Emit success event
    emit('loginSuccess', token)
  } catch (err: unknown) {
    const axiosError = axios.isAxiosError(err) ? err : null
    if (axiosError?.response?.status === 401) {
      error.value = 'Identifiant ou mot de passe incorrect'
    } else if (axiosError?.response?.data?.message) {
      error.value = axiosError.response.data.message
    } else {
      error.value = err instanceof Error ? err.message : 'Erreur lors de la connexion'
    }
    console.error('Erreur de connexion:', err)
  } finally {
    loading.value = false
  }
}

const handleKeydown = (event: KeyboardEvent) => {
  if (event.key === 'Enter') {
    handleSubmit()
  }
}
</script>

<template>
  <div
    class="min-h-screen bg-linear-to-br from-blue-50 to-indigo-100 flex items-center justify-center py-8 px-4 sm:px-6 lg:px-8">
    <div class="w-full max-w-md bg-white rounded-lg shadow-lg p-8">
      <div class="text-center mb-8">
        <h1 class="text-4xl font-bold text-gray-900">Connexion</h1>
        <p class="text-gray-600 mt-2">Gestion des Semestres et Étudiants</p>
      </div>

      <form @submit.prevent="handleSubmit" class="space-y-6">
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700 mb-2">
            Identifiant
          </label>
          <input id="username" v-model="form.username" type="text" required @keydown="handleKeydown"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-colors"
            placeholder="Entrez votre identifiant" />
        </div>

        <div>
          <label for="password" class="block text-sm font-medium text-gray-700 mb-2">
            Mot de passe
          </label>
          <input id="password" v-model="form.password" type="password" required @keydown="handleKeydown"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-colors"
            placeholder="Entrez votre mot de passe" />
        </div>

        <div v-if="error" class="text-red-600 bg-red-100 p-4 rounded-lg text-sm">
          {{ error }}
        </div>

        <button type="submit" :disabled="loading"
          class="w-full bg-blue-600 hover:bg-blue-700 disabled:bg-gray-400 disabled:cursor-not-allowed text-white font-semibold py-2 px-4 rounded-lg transition-colors duration-200">
          {{ loading ? 'Connexion en cours...' : 'Se connecter' }}
        </button>
      </form>

      <div class="mt-6 pt-6 border-t border-gray-200">
        <p class="text-center text-gray-600 text-sm">
          Utilisez vos identifiants d'administration
        </p>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
