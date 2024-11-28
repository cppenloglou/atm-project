"use client"
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
import { useState } from "react"

export function RegisterForm() {
    const { toast } = useToast();
    const router = useRouter();
    const [formData, setFormData] = useState({
        firstname: '',
        lastname: '',
        email: '',
        password: '',
        role: 'USER'
    });

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { id, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [id]: value
        }));
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/v1/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                router.push('/login');
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
              } else if (response.status == 409) {
                toast({
                  title: "Login Failed",
                  description: await response.text(),
                  variant: "destructive",
                })
              } else {
                console.error(response.status);
              }
        } catch (error) {
            console.error('Error during registration:', error);
        }
    };

    return (
        <Card className="mx-auto max-w-sm">
            <CardHeader>
                <CardTitle className="text-2xl">Register</CardTitle>
                <CardDescription>
                    Enter your information below to Register to your account
                </CardDescription>
            </CardHeader>
            <CardContent>
            <form onSubmit={handleSubmit} className="grid gap-4">
                <div className="grid gap-4">
                    <div className="grid gap-2">
                        <Label htmlFor="firstname">Firstname</Label>
                        <Input
                            id="firstname"
                            type="text"
                            placeholder="John"
                            required
                            value={formData.firstname}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="grid gap-2">
                        <Label htmlFor="lastname">Lastname</Label>
                        <Input
                            id="lastname"
                            type="text"
                            placeholder="Doe"
                            required
                            value={formData.lastname}
                            onChange={handleInputChange}
                        />
                    </div>
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
                        Register
                    </Button>
                    </div>
                </form>
            </CardContent>
        </Card>
    )
}
