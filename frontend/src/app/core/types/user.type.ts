export interface User {
  email: string | unknown,
  firstName: string | unknown,
  lastName: string | unknown,
  userName:string | unknown,
  role: string | unknown,
  avatar: string | unknown,
  id: number | unknown
  notLocked: boolean | unknown
}


export interface ForgotPassword {
  newPassword:string,
  confirmPassword:string,
  code:string,
  email:string
}

export interface ChangePassword {
  oldPassword:string,
  newPassword:string,
  confirmPassword:string
}

export interface ChangeInformation {
  firstName:string
  lastName:string
}