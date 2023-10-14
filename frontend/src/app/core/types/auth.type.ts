export interface Login {
  userName:string,
  password:string
}

export interface Register{
  userName:string,
  firstName:string,
  lastName:string,
  password:string,
  confirmPassword:string,
  email:string
}

export interface VerifyEmail{
  email:string,
  code:string,
}