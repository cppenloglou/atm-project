"use client"
import Image from "next/image";
import {Button} from "@/components/ui/button";
import {useRouter} from "next/navigation";

export default function Home() {

    const router = useRouter();

    const handleLoginClick = () => {
        router.push('/login'); // Redirects to the login page
    };

    const handleRegisterClick = () => {
        router.push('/register'); // Redirects to the login page
    };
  return (
    <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16 sm:p-20 font-[family-name:var(--font-geist-sans)]">

        <h1 className="text-9xl font-extrabold text-green-600 drop-shadow-md tracking-wide">
            ATM
        </h1>


        <main className="flex flex-col gap-8 row-start-2 items-center sm:items-start">
            <Image
                className="dark:invert"
                src="/man-withdrawing-money-from-atm.svg"
                alt="Atm logo"
                width={360 * 1.5}
                height={76 * 2}
                priority
            />

            <ol className="ml-28 list-inside list-decimal text-sm text-center sm:text-left font-[family-name:var(--font-geist-mono)]">
                <li className="mb-2">
                    Get started by login in {" "}
                    <code className="bg-black/[.05] dark:bg-white/[.06] px-1 py-0.5 rounded font-semibold">
                        Login Button
                    </code>
                    .
                </li>
                <li>Or press the {" "}
                    <code className="bg-black/[.05] dark:bg-white/[.06] px-1 py-0.5 rounded font-semibold">
                        Register Button
                    </code>
                    .
                </li>
            </ol>

            <div className="flex gap-4 items-center flex-col sm:flex-row">
                <Button variant="default" onClick={handleLoginClick} className="ml-40 rounded-full border border-solid border-transparent transition-colors flex items-center justify-center bg-primary text-background gap-2 hover:bg-[#383838] dark:hover:bg-[#ccc] text-sm sm:text-base h-10 sm:h-12 px-4 sm:px-5">
                    Login
                </Button>
                <Button variant="default" onClick={handleRegisterClick}
                        className="rounded-full border border-solid border-transparent transition-colors flex items-center justify-center bg-primary text-background gap-2 hover:bg-[#383838] dark:hover:bg-[#ccc] text-sm sm:text-base h-10 sm:h-12 px-4 sm:px-5">
                    Register
                </Button>
            </div>

        </main>
        <footer className="row-start-3 flex gap-6 flex-wrap items-center justify-center">
            <a
                className="flex items-center gap-2 hover:underline hover:underline-offset-4"
                href="https://github.com/cppenloglou"
                target="_blank"
                rel="noopener noreferrer"
            >
                <Image
                    aria-hidden
                    src="/icons8-github.svg"
                    alt="Github icon"
                    width={16 * 3}
                    height={16 * 3}
                />
                Developed By Chrysostomos P. Penloglou
            </a>
        </footer>
    </div>
  );
}
