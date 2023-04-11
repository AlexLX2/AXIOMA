import {Role} from "./role";

export interface UserRole {
    id?: number;
    user: string;
    role: Role[];
}
