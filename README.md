🎬 MovieList App

O MovieList é um aplicativo Android moderno que permite explorar os filmes mais populares do momento. O projeto foi desenvolvido com foco em UI responsiva e arquitetura escalável, utilizando as ferramentas mais atuais do ecossistema Android.

🎨 O design foi baseado neste [protótipo do Figma](https://www.figma.com/make/Vwm2wvKtkr7cPyCNuNovbN/Tela-de-listagem-de-filmes?p=f&t=Ag2QriblAfZbB1Ek-0&fullscreen=1).

🌐 API Consumida

O projeto consome a API oficial do The Movie Database (TMDB).

•Documentação utilizada: TMDB - Get Popular Movies

•Base URL das Imagens: https://image.tmdb.org/t/p/w500/

🏗️ Arquitetura e Padrões

O app foi construído seguindo os princípios da Clean Architecture, garantindo testabilidade e separação de conceitos.

Camadas:
•Domain: Regras de negócio puras, modelos (Movie) e definições de erros (DataError).Camada agnóstica de frameworks.

•Data: Consumo de rede com Retrofit, tratamento de DTOs e implementação de repositórios.

•Presentation: Implementada utilizando MVVM (Model-View-ViewModel) com UDF (Unidirectional Data Flow):

      •Single Source of Truth: A UI reage a um único objeto de estado reativo (MovieListUiState).
  
      •Unidirectional Flow: As ações da View (onAction) são enviadas à ViewModel, que processa a lógica e atualiza o estado.
  
      •Modular UI: Componentes extraídos para facilitar reuso e manutenção (MovieItem, MovieShimmer, FeedbackScreen).

🛠️ Tecnologias e Bibliotecas

•Jetpack Compose: UI declarativa moderna.

•Koin: Injeção de dependência simplificada para Android.

•Coroutines & Flow: Gerenciamento de tarefas assíncronas e fluxos de dados.

•Retrofit & OkHttp: Cliente HTTP para requisições de rede.

•Coil: Carregamento de imagens assíncrono.

•Material 3: Design System de última geração.

✨ Funcionalidades Implementadas

•✅ Listagem em grade responsiva.

•✅ Efeito Shimmer para carregamento (Skeleton UI).

•✅ Tratamento de erro de conexão e estados vazios.

•✅ Externalização total de Strings, Cores e Dimensões.

•✅ Testes unitários para mapeamento de erros e estados de UI.

🚀 Como rodar o projeto

1.Clone o repositório.

2.No seu arquivo de configuração, adicione sua API_KEY do TMDB.

3.Sincronize o Gradle no Android Studio.

4.Execute em um emulador ou dispositivo físico
  
