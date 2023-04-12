import {Company} from "./company";

export interface Role {
    id?: number;
    name: string;
    company?: Company;
}
