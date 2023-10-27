export interface Notification {
    id: number,
    link: string,
    message: string,
    read: boolean,
    createdAt: string
}

export interface RpNotification {
    unSeenNotification: number,
    notifications: Notification[]
}