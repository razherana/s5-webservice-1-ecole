<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import axiosInstance from '@/utils/axiosInstance'
import api from '@/utils/api'

interface Etudiant {
  id: number
  nom: string
  prenom: string
  [key: string]: string | number | boolean | undefined
}

interface EtudiantAvecMoyenne extends Etudiant {
  moyenne: number | undefined
}

interface Props {
  semestreId: number | null
}

const props = withDefaults(defineProps<Props>(), {
  semestreId: null
})

const etudiants = ref<EtudiantAvecMoyenne[]>([])
const loading = ref(false)
const error = ref<string | null>(null)

const fetchEtudiants = async () => {
  if (!props.semestreId) {
    etudiants.value = []
    return
  }

  loading.value = true
  error.value = null
  try {
    const response = await axiosInstance.get(api('/api/etudiants'))
    etudiants.value = response.data.data.etudiants || []

    // Fetch moyennes for each etudiant in this semestre
    const etudiantsAvecMoyennes = await Promise.all(
      etudiants.value.map(async (etudiant) => {
        try {
          const notesResponse = await axiosInstance.get(
            api(`/api/notes/semestres/${props.semestreId}/etudiants/${etudiant.id}`)
          )
          const notes = notesResponse.data.data.notes || []

          // Calculate average
          if (notes.length > 0) {
            const total = notes.reduce((sum: number, note: { note?: number; valeur?: number }) => {
              return sum + ((note.note || note.valeur) ?? 0)
            }, 0)
            const moyenne = total / notes.length
            return { ...etudiant, moyenne }
          }
          return { ...etudiant, moyenne: 0 }
        } catch (err) {
          console.error(`Erreur lors du chargement des notes pour l'étudiant ${etudiant.id}:`, err)
          return { ...etudiant, moyenne: 0 }
        }
      })
    )

    etudiants.value = etudiantsAvecMoyennes.sort((a, b) => {
      // Sort by moyenne descending
      const moyenneA = a.moyenne ?? 0
      const moyenneB = b.moyenne ?? 0
      return moyenneB - moyenneA
    })
  } catch (err: unknown) {
    const errorMessage = err instanceof Error ? err.message : 'Erreur lors du chargement des étudiants'
    error.value = errorMessage
    console.error('Erreur:', err)
  } finally {
    loading.value = false
  }
}

const getMoyenneColor = (moyenne: number | undefined) => {
  if (!moyenne) return 'text-gray-600'
  if (moyenne >= 16) return 'text-green-600'
  if (moyenne >= 14) return 'text-blue-600'
  if (moyenne >= 12) return 'text-yellow-600'
  return 'text-red-600'
}

watch(() => props.semestreId, () => {
  fetchEtudiants()
})

onMounted(() => {
  fetchEtudiants()
})
</script>

<template>
  <div class="bg-white rounded-lg shadow mt-6">
    <div class="p-6">
      <h2 class="text-2xl font-bold text-gray-800 mb-6">Étudiants et Moyennes</h2>

      <div v-if="!semestreId" class="text-center py-8">
        <p class="text-gray-500">Sélectionnez un semestre pour voir les étudiants</p>
      </div>

      <div v-else>
        <div v-if="loading" class="text-center py-8">
          <p class="text-gray-600">Chargement des étudiants...</p>
        </div>

        <div v-if="error" class="text-red-600 bg-red-100 p-4 rounded mb-4">
          {{ error }}
        </div>

        <div v-if="!loading && etudiants.length === 0" class="text-center py-8">
          <p class="text-gray-500">Aucun étudiant disponible</p>
        </div>

        <div v-if="!loading && etudiants.length > 0" class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="border-b-2 border-gray-300">
                <th class="px-4 py-3 text-left text-gray-700 font-semibold">Nom</th>
                <th class="px-4 py-3 text-left text-gray-700 font-semibold">Prénom</th>
                <th class="px-4 py-3 text-right text-gray-700 font-semibold">Moyenne</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="etudiant in etudiants"
                :key="etudiant.id"
                class="border-b border-gray-200 hover:bg-gray-50 transition-colors"
              >
                <td class="px-4 py-3 text-gray-800">{{ etudiant.nom }}</td>
                <td class="px-4 py-3 text-gray-800">{{ etudiant.prenom }}</td>
                <td class="px-4 py-3 text-right font-bold" :class="getMoyenneColor(etudiant.moyenne)">
                  {{ etudiant.moyenne !== undefined ? etudiant.moyenne.toFixed(2) : '-' }} / 20
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
