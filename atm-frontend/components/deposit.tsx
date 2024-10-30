"use client"
import { AppSidebar } from "@/components/app-sidebar"
import { SidebarProvider } from "./ui/sidebar"
import { Card, CardContent, CardHeader, CardTitle } from "./ui/card"
import { useState } from "react"
import { useToast } from "@/hooks/use-toast"
export default function DepositPage() {
  const [amount, setAmount] = useState("")

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    
    try {
      const token = localStorage.getItem("token")
      const accountInfo = JSON.parse(localStorage.getItem("accountInfo") || "{}")
      const accountId = accountInfo.id

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

      if (response.ok) {
        setAmount("")
        const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}');
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
          console.error('Failed to fetch updated account info');
        }
      } else {
        console.error("Deposit failed");
      }
    } catch (error) {
      console.error("Error making deposit:", error)
    }
  }

  return (
    <div className="flex h-screen bg-slate-50 w-screen">
      <SidebarProvider>
        <AppSidebar />
        <main className="flex-1 p-6 flex flex-col items-center justify-center">
          <Card className="w-[400px]">
            <CardHeader>
              <CardTitle>Deposit Money</CardTitle>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit} className="space-y-4">
                <div className="space-y-2">
                  <label className="text-sm font-medium">Amount</label>
                  <input 
                    type="number"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    className="w-full p-2 border rounded"
                    placeholder="Enter amount"
                    required
                  />
                </div>
                <button 
                  type="submit"
                  className="bg-primary text-white px-4 py-2 rounded w-full"
                >
                  Deposit
                </button>
              </form>
            </CardContent>
          </Card>
        </main>
      </SidebarProvider>
    </div>
  )
}