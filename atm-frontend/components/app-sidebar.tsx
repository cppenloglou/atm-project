"use client"

import { LogOut, Home, BookUp2, BookDown, User2, ChevronUp} from "lucide-react"
import { useEffect, useState } from "react"
import { useToast } from "@/hooks/use-toast"
import { Toaster } from "@/components/ui/toaster"
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar"
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "@radix-ui/react-dropdown-menu"

const items = [
  {
    title: "Home",
    url: "/main",
    icon: Home,
  },
  {
    title: "Withdraw",
    url: "/withdraw",
    icon: BookUp2,
  },
  {
    title: "Deposit",
    url: "/deposit",
    icon: BookDown,
  },
]

const dropdown_items = [
  {
    title: "Logout",
    url: "/",
    icon: LogOut
  },
]

export function AppSidebar() {
  const [userInfo, setUserInfo] = useState<{firstname: string} | null>(null);
  const { toast } = useToast();

  useEffect(() => {
    const storedUserInfo = localStorage.getItem('userInfo');
    if (storedUserInfo) {
      setUserInfo(JSON.parse(storedUserInfo));
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('userInfo');
    
    toast({
      title: "Logged Out",
      description: "You have been successfully logged out.",
    });

    setTimeout(() => {
      window.location.href = '/';
    }, 500);
  }

  return (
    <>
      <Toaster />
      <Sidebar>
        <SidebarContent>
          <SidebarGroup>
            <SidebarGroupLabel>ATM Menu</SidebarGroupLabel>
            <SidebarGroupContent>
              <SidebarMenu>
                {items.map((item) => (
                  <SidebarMenuItem key={item.title}>
                    <SidebarMenuButton asChild>
                      <a href={item.url}>
                        <item.icon />
                        <span>{item.title}</span>
                      </a>
                    </SidebarMenuButton>
                  </SidebarMenuItem>
                ))}
              </SidebarMenu>
            </SidebarGroupContent>
          </SidebarGroup>
        </SidebarContent>
        <SidebarFooter>
          <SidebarMenu>
            <SidebarMenuItem>
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <SidebarMenuButton>
                    <User2 /> {userInfo?.firstname || "Loading..."}
                    <ChevronUp className="ml-auto" />
                  </SidebarMenuButton>
                </DropdownMenuTrigger>
                <DropdownMenuContent
                  side="top"
                  className="w-56"
                >{dropdown_items.map((item) => (
                  <DropdownMenuItem key={item.title}>
                    <SidebarMenuButton onClick={handleLogout}>
                      <item.icon />
                      <span>{item.title}</span>
                    </SidebarMenuButton>
                  </DropdownMenuItem>
                ))}
                </DropdownMenuContent>
              </DropdownMenu>
            </SidebarMenuItem>
          </SidebarMenu>
        </SidebarFooter>
      </Sidebar>
    </>
  )
}