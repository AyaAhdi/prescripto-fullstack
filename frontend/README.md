# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Babel](https://babeljs.io/) (or [oxc](https://oxc.rs) when used in [rolldown-vite](https://vite.dev/guide/rolldown)) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.



Tailwind CSS
  ==> Installer le plugin Tailwind pour Vite : npm install -D @tailwindcss/vite

  ==> Modifier vite.config.js :
    import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [react(), tailwindcss()],
})

  ==> Ajouter Tailwind dans le CSS : @import "tailwindcss";

  ==> Vérifier que ton CSS est importé : import './index.css'

  ==> Tester Tailwind : Ajouter dans App.jsx
    <!-- export default function App() {
    return (
    <div className="flex items-center justify-center h-screen">
      <h1 className="text-4xl font-bold text-blue-600">
        Tailwind v4 fonctionne 🚀
      </h1>
    </div>
    )
    } -->
  
  ==> Lancer le projet : npm run dev

  ==> 04:35:33
  ==> 06:04:00
