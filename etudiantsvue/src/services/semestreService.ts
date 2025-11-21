import axiosInstance from '@/utils/axiosInstance'
import api from '@/utils/api'
import type { APIRespone, Semestre, Option } from '@/types'

class SemestreService {
  async fetchSemestres(): Promise<Semestre[]> {
    try {
      const response = await axiosInstance.get(api('/api/semestres'))

      const responseData = response.data as APIRespone<{
        semestres: Semestre[]
      }>

      console.log('Données des semestres reçues:', responseData)

      return responseData.data?.semestres || []
    } catch (err: unknown) {
      const errorMessage = err instanceof Error ? err.message : 'Erreur lors du chargement des semestres'
      console.error('Erreur:', err)
      throw new Error(errorMessage)
    }
  }

  async fetchOptions(): Promise<Option[]> {
    try {
      const response = await axiosInstance.get(api('/api/options'))

      const responseData = response.data as APIRespone<{
        options: Option[]
      }>

      console.log('Données des options reçues:', responseData.data?.options)

      return responseData.data?.options || []
    } catch (err: unknown) {
      console.error('Erreur lors du chargement des options:', err)
      throw new Error(err instanceof Error ? err.message : 'Erreur lors du chargement des options')
    }
  }
}

export default new SemestreService()
