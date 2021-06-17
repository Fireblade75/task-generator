import { Category } from "./category";

export interface Task {
    name: string,
    description: string,
    done: boolean,
    categories: Category[],
    projectId: number
}