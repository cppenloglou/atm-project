"use client"

import { useState } from "react"
import { useRouter } from "next/navigation"
import { AppSidebar } from "@/components/app-sidebar"
import { SidebarProvider } from "@/components/ui/sidebar"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import { useToast } from "@/hooks/use-toast"

export default function WithdrawPage() {
  const [amount, setAmount] = useState("")
  const [isLoading, setIsLoading] = useState(false)
  const router = useRouter()
  const { toast } = useToast()

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    setIsLoading(true)

    try {
      const token = localStorage.getItem("token")
      const accountInfo = JSON.parse(localStorage.getItem("accountInfo") || "{}")
      const accountId = accountInfo.id

      if (!token || !accountId) {
        throw new Error("Authentication information is missing")
      }

      const response = await fetch(`http://localhost:8080/api/v1/transaction`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({
          accountId: accountId,
          amount: parseFloat(amount),
          type: "WITHDRAWAL"
        })
      })

      if (!response.ok) {
        const errorData = await response.json()
        throw new Error(errorData.error)
      }

      setAmount("")
      await updateAccountInfo(token)
      toast({
        title: "Success",
        description: "Withdrawal completed successfully",
        variant: "default",
      })
      router.push("/main") // Redirect to dashboard after successful withdrawal
    } catch (error) {
      console.error("Error making withdrawal:", error)
      toast({
        title: "Error",
        description: error instanceof Error ? error.message : "An unexpected error occurred",
        variant: "destructive",
      })
    } finally {
      setIsLoading(false)
    }
  }

  const updateAccountInfo = async (token: string) => {
    const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
    const accountResponse = await fetch(`http://localhost:8080/api/v1/users/${userInfo.id}/accounts`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })

    if (accountResponse.ok) {
      const accountInfo = await accountResponse.json()
      localStorage.setItem('accountInfo', JSON.stringify(accountInfo))
    } else {
      throw new Error('Failed to fetch updated account info')
    }
  }

  return (
    <div className="flex h-screen bg-slate-50 w-screen">
      <SidebarProvider>
        <AppSidebar />
        <main className="flex-1 p-6 flex flex-col items-center justify-center">
          <Card className="w-[400px]">
            <CardHeader>
              <CardTitle>Withdraw Money</CardTitle>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit} className="space-y-4">
                <div className="space-y-2">
                  <Label htmlFor="amount">Amount</Label>
                  <Input
                    id="amount"
                    type="number"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    placeholder="Enter amount"
                    required
                    min="0.01"
                    step="0.01"
                  />
                </div>
                <Button type="submit" className="w-full" disabled={isLoading}>
                  {isLoading ? "Processing..." : "Withdraw"}
                </Button>
              </form>
            </CardContent>
          </Card>
        </main>
      </SidebarProvider>
    </div>
  )
}