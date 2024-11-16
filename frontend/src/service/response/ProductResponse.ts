export interface ProductResponse {
  id: string;
  name: string;
  price: number;
  description: string;
  color: string;
  manufacturerName: string;
  imgRef: string;
  characteristics: Record<string, string>;
}