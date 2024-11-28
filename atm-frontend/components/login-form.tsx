"use client"
import Link from "next/link"
import { useState } from "react"

import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { useRouter } from "next/navigation"
import { useToast } from "@/hooks/use-toast"


export function LoginForm() {
  const { toast } = useToast();
  const router = useRouter();
  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [id]: value
    }));
  };

  const fetchUserInfo = async (token: string) => {
    try {
      const response = await fetch(`http://localhost:8080/api/v1/users/by-token?token=${token}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });

      if (response.ok) {
        const userInfo = await response.json();
        localStorage.setItem('userInfo', JSON.stringify(userInfo));
        
        // Fetch account info using user ID
        const accountResponse = await fetch(`http://localhost:8080/api/v1/users/${userInfo.id}/accounts`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          }
        });

        if (accountResponse.ok) {
          const accountInfo = await accountResponse.json();
          localStorage.setItem('accountInfo', JSON.stringify(accountInfo));
        } else {
          console.error('Failed to fetch account info');
        }

      } else {
        console.error('Failed to fetch user info');
      }
    } catch (error) {
      console.error('Error fetching user info:', error);
    }
  };


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/v1/auth/authenticate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
      });

      if (response.ok) {
        const data = await response.json();
        const accessToken = data.access_token;
        const refreshToken = data.refresh_token;
        console.log(accessToken);
        localStorage.setItem('token', accessToken);
        localStorage.setItem('refreshToken', refreshToken);
        await fetchUserInfo(accessToken);
        router.push('/main');
      } else if (response.status == 400) {
        const parsedData = JSON.parse(((await response.text()).toString()));



        function getAllValues<T extends Record<string, unknown>>(obj: T): unknown[] {
          return Object.values(obj).flatMap(value => {
            if (typeof value === 'object' && value !== null) {
              // Assert that value is a Record<string, unknown>
              return getAllValues(value as Record<string, unknown>);
            }
            return value;
          });
        }
        

        // Convert values to comma-separated string
        const valuesString = getAllValues(parsedData).join(',');
        toast({
          title: "Login Failed",
          description: valuesString,
          variant: "destructive",
        })
      } else {
        console.error(response.status);
      }
    } catch (error) {
      console.error('Error during login:', error);
    }
  };

  return (
    <Card className="mx-auto max-w-sm">
      <CardHeader>
        <CardTitle className="text-2xl">Login</CardTitle>
        <CardDescription>
          Enter your email below to login to your account
        </CardDescription>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className="grid gap-4">
          <div className="grid gap-2">
            <Label htmlFor="email">Email</Label>
            <Input
              id="email"
              type="email"
              placeholder="m@example.com"
              required
              value={formData.email}
              onChange={handleInputChange}
            />
          </div>
          <div className="grid gap-2">
            <div className="flex items-center">
              <Label htmlFor="password">Password</Label>
              <Link href="#" className="ml-auto inline-block text-sm underline">
                Forgot your password?
              </Link>
            </div>
            <Input 
              id="password" 
              type="password" 
              required 
              value={formData.password}
              onChange={handleInputChange}
            />
          </div>
          <Button type="submit" className="w-full">
            Login
          </Button>
        </form>
        <div className="mt-4 text-center text-sm">
          Don&apos;t have an account?{" "}
          <Link href="/register" className="underline">
            Sign up
          </Link>
        </div>
      </CardContent>
    </Card>
  )
}
