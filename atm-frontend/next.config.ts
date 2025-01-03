import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  env: {
    NEXT_PUBLIC_API_URL: 'http://192.168.49.2:30001',
  },
};

export default nextConfig;
