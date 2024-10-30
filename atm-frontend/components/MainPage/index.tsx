"use client"

import { useEffect, useState } from "react"
import { SidebarProvider } from "@/components/ui/sidebar"
import { AppSidebar } from "@/components/app-sidebar"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"

interface UserInfo {
  id: number;
  firstname: string;
  lastname: string;
  email: string;
}

interface AccountInfo {
  balance: number;
}

export function MainPage() {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const [accountInfo, setAccountInfo] = useState<AccountInfo | null>(null);

  useEffect(() => {
    const storedUserInfo = localStorage.getItem('userInfo');
    const storedAccountInfo = localStorage.getItem('accountInfo');
    if (storedUserInfo) {
      setUserInfo(JSON.parse(storedUserInfo));
    }
    if (storedAccountInfo) {
      setAccountInfo(JSON.parse(storedAccountInfo));
    }
  }, []);

  return (
    <>
      <SidebarProvider>
        <div className="flex">
          <AppSidebar />
          <main className="flex-1 bg-gradient-to-br from-blue-50 via-white to-purple-50 w-screen">
            <div className="grid grid-cols-3 min-h-screen p-6 bg-[radial-gradient(ellipse_at_top,_var(--tw-gradient-stops))] from-sky-100 via-transparent to-transparent">
              <div className="col-span-2 flex justify-center items-center">
                {userInfo && (
                  <div className="flex flex-col gap-4">
                    <Card className="w-[400px] shadow-lg backdrop-blur-sm bg-white/80 transition-all duration-300 hover:scale-105 hover:shadow-xl hover:bg-white/90">
                      <CardHeader className="text-center">
                        <CardTitle className="text-2xl">User Profile</CardTitle>
                      </CardHeader>
                      <CardContent>
                        <div className="space-y-4 text-lg">
                          <div>
                            <span className="font-semibold">Name: </span>
                            {userInfo.firstname} {userInfo.lastname}
                          </div>
                          <div>
                            <span className="font-semibold">Email: </span>
                            {userInfo.email}
                          </div>
                        </div>
                      </CardContent>
                    </Card>

                    <Card className="w-[400px] shadow-lg backdrop-blur-sm bg-white/80 transition-all duration-300 hover:scale-105 hover:shadow-xl hover:bg-white/90">
                      <CardHeader className="text-center">
                        <CardTitle className="text-2xl">Account Balance</CardTitle>
                      </CardHeader>
                      <CardContent>
                        <div className="text-center">
                          <span className="text-3xl font-bold">
                            ${accountInfo?.balance?.toFixed(2) || '0.00'}
                          </span>
                        </div>
                      </CardContent>
                    </Card>
                  </div>
                )}
              </div>
            </div>
          </main>
        </div>
      </SidebarProvider>
    </>
  );
}
