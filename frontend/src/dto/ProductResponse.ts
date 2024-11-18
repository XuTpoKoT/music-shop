import { z } from 'zod';

export const ProductResponseSchema = z.object({
  id: z.string(),
  name: z.string(),
  price: z.number(),
  description: z.string(),
  color: z.string(),
  manufacturerName: z.string(),
  imgRef: z.string(),
  characteristics: z.record(z.string()),
});

export type ProductResponse = z.infer<typeof ProductResponseSchema>;
