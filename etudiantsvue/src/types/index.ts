export interface Semestre {
  id: number
  libelle: string
  annee: string
}

export interface Option {
  id: number
  libelle: string
  code: string
  semestreId: number
}

export interface Etudiant {
  id: number
  nom: string
  prenom: string
  [key: string]: string | number | boolean | undefined
}

export interface EtudiantAvecMoyenne extends Etudiant {
  moyenne: number | undefined
}

export interface APIRespone<S> {
  status: 'success' | 'error'
  data: S | null
  error: {
    code: number
    message: string
    details?: { [key: string]: string }
  } | null
}

export interface LoginForm {
  username: string
  password: string
}
