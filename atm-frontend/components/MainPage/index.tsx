
import { SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar"
import { AppSidebar } from "@/components/app-sidebar"


export function MainPage() {
  return (
    <>
      <SidebarProvider>
      <AppSidebar />
      <main>
        <SidebarTrigger />
        <div className="flex flex-row items-center w-full bg-slate-200">
          
        </div>
      </main>
    </SidebarProvider>
    </>
  );
}
