<script setup lang="ts">
import { ref, onMounted } from 'vue'
import FormulaireConnexion from '@/components/FormulaireConnexion.vue'
import ListeSemestres from '@/components/ListeSemestres.vue'
import ListeEtudiants from '@/components/ListeEtudiants.vue'
import type { Semestre } from './types'

const isAuthenticated = ref(false)
const selectedSemestre = ref<Semestre | null>(null)

const handleSelectSemestre = (semestre: Semestre) => {
  selectedSemestre.value = semestre
}

const handleLoginSuccess = (token: string) => {
  isAuthenticated.value = true
  // Setup axios header for subsequent requests
  localStorage.setItem('authToken', token)
}

const handleLogout = () => {
  isAuthenticated.value = false
  selectedSemestre.value = null
  localStorage.removeItem('authToken')
}

onMounted(() => {
  // Check if user is already authenticated
  const token = localStorage.getItem('authToken')
  if (token) {
    isAuthenticated.value = true
  }
})
</script>

<template>
  <div v-if="!isAuthenticated">
    <FormulaireConnexion @login-success="handleLoginSuccess" />
  </div>

  <div v-else class="min-h-screen bg-gray-100 py-8 px-4 sm:px-6 lg:px-8">
    <div class="max-w-6xl mx-auto">
      <header class="mb-8 flex justify-between items-center">
        <div>
          <h1 class="text-4xl font-bold text-gray-900">Gestion des Semestres et Étudiants</h1>
          <p class="text-gray-600 mt-2">Consultez les semestres et les moyennes des étudiants</p>
        </div>
        <button @click="handleLogout"
          class="bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-6 rounded-lg transition-colors duration-200">
          Déconnexion
        </button>
      </header>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div class="lg:col-span-1">
          <ListeSemestres @select-semestre="handleSelectSemestre" />
        </div>

        <div class="lg:col-span-2">
          <div v-if="selectedSemestre" class="bg-white rounded-lg shadow p-6 mb-6">
            <p class="text-lg text-gray-700">
              <span class="font-semibold">Semestre sélectionné :</span>
              <span class="text-blue-600 font-bold ml-2"> {{ selectedSemestre.annee }} - {{ selectedSemestre.libelle }}</span>
            </p>
          </div>

          <ListeEtudiants :semestre-id="selectedSemestre?.id ?? null" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
