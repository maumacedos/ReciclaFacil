# ♻️ Recicla Fácil - MVP

**Projeto Acadêmico:** Módulo de Desenvolvimento Mobile.

## 📱 Sobre o Projeto
O **Recicla Fácil** é uma solução mobile nativa para Android desenvolvida em Kotlin. O app ajuda cidadãos a encontrarem pontos de coleta seletiva próximos, além de educar sobre o tempo de decomposição de diferentes materiais.

---

## 🏗️ Arquitetura e Decisões Técnicas
- **Padrão Arquitetural:** MVVM (Model-View-ViewModel).
  - *Por que?* Garante que a lógica de filtragem de locais e o estado da cidade selecionada sobrevivam a mudanças de configuração (como girar a tela) e facilita a manutenção do código.
- **Linguagem:** Kotlin 1.9+.
- **UI:** Material Design 3, ViewPager2 (para os cards de materiais) e RecyclerView (para a lista de locais).

---

## 📂 Como testar o aplicativo
Para instalar o produto final no seu celular:
1. Acesse a pasta `executavel/`.
2. Baixe o arquivo `.apk`.
3. No Android, permita a instalação de fontes desconhecidas e execute o arquivo.

---

## 🛠️ Dependências para Desenvolvedores
Se desejar rodar o código-fonte:
- Android Studio Ladybug | 2024.2.1 ou superior.
- Java 17 (JDK).
- Gradle 8.0+.
