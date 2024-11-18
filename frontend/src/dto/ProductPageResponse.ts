import { z } from 'zod';
import { ProductResponseSchema } from './ProductResponse';

export const ProductPageResponseSchema = z.object({
  totalPages: z.number(),
  currentPage: z.number(),
  content: z.array(ProductResponseSchema),
});

export type ProductPageResponse = z.infer<typeof ProductPageResponseSchema>;