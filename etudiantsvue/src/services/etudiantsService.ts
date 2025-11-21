import axiosInstance from '@/utils/axiosInstance'
import api from '@/utils/api'
import type { APIRespone, Etudiant, EtudiantAvecMoyenne } from '@/types'

class EtudiantsService {
  async fetchEtudiants(): Promise<Etudiant[]> {
    try {
      const response = await axiosInstance.get(api('/api/etudiants'))
      const responseData = response.data as APIRespone<{
        etudiants: Etudiant[]
      }>

      console.log('Données des étudiants reçues:', responseData)

      return responseData.data?.etudiants || []
    } catch (err: unknown) {
      const errorMessage =
        err instanceof Error ? err.message : 'Erreur lors du chargement des étudiants'
      console.error('Erreur:', err)
      throw new Error(errorMessage)
    }
  }

  async fetchNotesBySemestreAndEtudiant(
    semestreId: number,
    etudiantId: number,
  ): Promise<Array<{ note?: number; valeur?: number }>> {
    try {
      const response = await axiosInstance.get(
        api(`/api/notes/semestres/${semestreId}/etudiants/${etudiantId}`),
      )
      const responseData = response.data as APIRespone<{
        notes: Array<{ note?: number; valeur?: number }>
      }>

      console.log(`Données des notes reçues pour étudiant ${etudiantId}:`, responseData)

      return responseData.data?.notes || []
    } catch (err: unknown) {
      console.error(`Erreur lors du chargement des notes pour l'étudiant ${etudiantId}:`, err)
      return []
    }
  }

  async fetchEtudiantsAvecMoyennes(semestreId: number): Promise<EtudiantAvecMoyenne[]> {
    try {
      const etudiants = await this.fetchEtudiants()

      const etudiantsAvecMoyennes = await Promise.all(
        etudiants.map(async (etudiant) => {
          const notes = await this.fetchNotesBySemestreAndEtudiant(semestreId, etudiant.id)

          if (notes.length > 0) {
            const total = notes.reduce((sum: number, note: { note?: number; valeur?: number }) => {
              return sum + ((note.note || note.valeur) ?? 0)
            }, 0)
            const moyenne = total / notes.length
            return { ...etudiant, moyenne }
          }
          return { ...etudiant, moyenne: 0 }
        }),
      )

      return etudiantsAvecMoyennes.sort((a, b) => {
        const moyenneA = a.moyenne ?? 0
        const moyenneB = b.moyenne ?? 0
        return moyenneB - moyenneA
      })
    } catch (err: unknown) {
      const errorMessage =
        err instanceof Error ? err.message : 'Erreur lors du chargement des étudiants avec moyennes'
      console.error('Erreur:', err)
      throw new Error(errorMessage)
    }
  }
}

export default new EtudiantsService()
