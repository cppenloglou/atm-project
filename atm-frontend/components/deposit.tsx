"use client"

import { useState } from "react"
import { AppSidebar } from "@/components/app-sidebar"
import { SidebarProvider } from "@/components/ui/sidebar"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { Label } from "@/components/ui/label"
import { useToast } from "@/hooks/use-toast"
import { Loader2 } from "lucide-react"
import { useRouter } from "next/navigation"

export default function DepositPage() {
  const [amount, setAmount] = useState("")
  const [isLoading, setIsLoading] = useState(false)
  const { toast } = useToast()
  const router = useRouter()

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

      const response = await fetch("http://localhost:8080/api/v1/transaction", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({
          accountId: accountId,
          amount: parseFloat(amount),
          type: "DEPOSIT"
        })
      })

      if (!response.ok) {
        throw new Error("Deposit failed")
      }

      setAmount("")
      toast({
        title: "Deposit Successful",
        description: `$${amount} has been deposited into your account.`,
      })

      await updateAccountInfo(token)
      router.push("/main")
    } catch (error) {
      console.error("Error making deposit:", error)
      toast({
        title: "Deposit Failed",
        description: error instanceof Error ? error.message : "An unexpected error occurred",
        variant: "destructive",
      })
    } finally {
      setIsLoading(false)
    }
  }

  const updateAccountInfo = async (token: string) => {
    try {
      const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
      const accountResponse = await fetch(`http://localhost:8080/api/v1/users/${userInfo.id}/accounts`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      })

      if (!accountResponse.ok) {
        throw new Error('Failed to fetch updated account info')
      }

      const accountInfo = await accountResponse.json()
      localStorage.setItem('accountInfo', JSON.stringify(accountInfo))
    } catch (error) {
      console.error("Error updating account info:", error)
      toast({
        title: "Update Failed",
        description: "Failed to update account information",
        variant: "destructive",
      })
    }
  }

  return (
    <div className="flex h-screen bg-slate-50 w-screen">
      <SidebarProvider>
        <AppSidebar />
        <main className="flex-1 p-6 flex flex-col items-center justify-center">
          <Card className="w-full max-w-md">
            <CardHeader>
              <CardTitle>Deposit Money</CardTitle>
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
                  {isLoading ? (
                    <>
                      <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                      Processing...
                    </>
                  ) : (
                    "Deposit"
                  )}
                </Button>
              </form>
            </CardContent>
          </Card>
        </main>
      </SidebarProvider>
    </div>
  )
}