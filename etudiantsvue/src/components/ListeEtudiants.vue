<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { etudiantsService, semestreCalculsService } from '@/services'
import EtudiantDetailModal from './EtudiantDetailModal.vue'
import type { EtudiantAvecMoyenne, StudentOptionDetail } from '@/types'
import type { SemestreAverage } from '@/services/semestreCalculsService'

interface Props {
  semestreId: number | null
}

const props = withDefaults(defineProps<Props>(), {
  semestreId: null
})

const etudiants = ref<EtudiantAvecMoyenne[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const selectedEtudiant = ref<EtudiantAvecMoyenne | null>(null)
const modalOpen = ref(false)
const optionsDetails = ref<StudentOptionDetail[]>([])
const optionsLoading = ref(false)
const optionsError = ref<string | null>(null)
const semestreMoyennes = ref<SemestreAverage[]>([])
const semestreMoyennesLoading = ref(false)
const semestreMoyennesError = ref<string | null>(null)
const currentCycle = ref<'ALL' | 'L1' | 'L2'>('ALL')

const loadSemestreMoyennes = async (etudiantId: number) => {
  semestreMoyennesError.value = null
  currentCycle.value = 'ALL'
  semestreMoyennesLoading.value = true

  try {
    semestreMoyennes.value = await semestreCalculsService.loadSemestreMoyennes(etudiantId)
  } catch (err: unknown) {
    const message = err instanceof Error ? err.message : 'Erreur lors du calcul des moyennes par semestre'
    semestreMoyennesError.value = message
    console.error('Erreur:', err)
    semestreMoyennes.value = []
  } finally {
    semestreMoyennesLoading.value = false
  }
}

const loadEtudiants = async () => {
  if (!props.semestreId) {
    etudiants.value = []
    return
  }

  loading.value = true
  error.value = null
  try {
    etudiants.value = await etudiantsService.fetchEtudiantsAvecMoyennes(props.semestreId)
  } catch (err: unknown) {
    const errorMessage = err instanceof Error ? err.message : 'Erreur lors du chargement des étudiants'
    error.value = errorMessage
    console.error('Erreur:', err)
  } finally {
    loading.value = false
  }
}

const openModal = async (etudiant: EtudiantAvecMoyenne) => {
  selectedEtudiant.value = etudiant
  modalOpen.value = true
  optionsLoading.value = true
  optionsError.value = null
  optionsDetails.value = []

  await loadSemestreMoyennes(etudiant.id)

  try {
    if (props.semestreId) {
      optionsDetails.value = await etudiantsService.fetchStudentOptionsDetails(
        props.semestreId,
        etudiant.id
      )
    }
  } catch (err: unknown) {
    const errorMessage = err instanceof Error ? err.message : 'Erreur lors du chargement des options'
    optionsError.value = errorMessage
    console.error('Erreur:', err)
  } finally {
    optionsLoading.value = false
  }
}

const closeModal = () => {
  modalOpen.value = false
  selectedEtudiant.value = null
  optionsDetails.value = []
  semestreMoyennes.value = []
  currentCycle.value = 'ALL'
}

const getMoyenneColor = (moyenne: number | undefined) => {
  return semestreCalculsService.getMoyenneColor(moyenne)
}

const handleCycleChange = (cycle: 'ALL' | 'L1' | 'L2') => {
  currentCycle.value = cycle
}

watch(() => props.semestreId, () => {
  loadEtudiants()
})

onMounted(() => {
  loadEtudiants()
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
              <tr v-for="etudiant in etudiants" :key="etudiant.id" @click="openModal(etudiant)"
                class="border-b border-gray-200 hover:bg-blue-50 transition-colors cursor-pointer">
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

  <EtudiantDetailModal
    :open="modalOpen"
    :selected-etudiant="selectedEtudiant"
    :options-details="optionsDetails"
    :options-loading="optionsLoading"
    :options-error="optionsError"
    :semestre-moyennes="semestreMoyennes"
    :semestre-moyennes-loading="semestreMoyennesLoading"
    :semestre-moyennes-error="semestreMoyennesError"
    :current-cycle="currentCycle"
    @close="closeModal"
    @cycle-chanage="handleCycleChange"
  />
</template>

<style scoped></style>
