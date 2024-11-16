import $api, {ErrorResponse} from "../http";
import {AxiosError} from "axios";
import { CategoryResponse } from "./response/CategoryResponse";

export default class CategoryService {

    static async getCategories() {
        return await $api.get<CategoryResponse[]>('/categories')
            .then(response => response.data)
    }
}

