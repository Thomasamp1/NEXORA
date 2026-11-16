
import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [statusApi, setStatusApi] = useState(null)
  const [erroApi, setErroApi] = useState(false)

  useEffect(() => {
    fetch('http://localhost:8080/api/status')
      .then((resposta) => {
        if (!resposta.ok) {
          throw new Error('Falha na comunicação com a API')
        }

        return resposta.json()
      })
      .then((dados) => {
        setStatusApi(dados)
        setErroApi(false)
      })
      .catch((erro) => {
        console.error('Erro ao conectar com a API:', erro)
        setErroApi(true)
      })
  }, [])

  return (
    <div className="app">
      <header className="navbar">
        <a href="#" className="logo">
          NEXORA<span>.</span>
        </a>

        <nav>
          <a href="#inicio">Início</a>
          <a href="#cursos">Cursos</a>
          <a href="#sobre">Sobre</a>
        </nav>

        <div className="nav-actions">
          <button className="btn-login">Entrar</button>
          <button className="btn-primary">Começar agora</button>
        </div>
      </header>

      <main>
        <section className="hero-section" id="inicio">
          <div className="hero-content">
            <span className="eyebrow">
              APRENDA. PRATIQUE. EVOLUA.
            </span>

            <h1>
              Seu futuro na tecnologia começa <span>aqui.</span>
            </h1>

            <p>
              Aprenda programação e desenvolvimento de software
              com cursos organizados, aulas práticas e desafios
              que acompanham sua evolução.
            </p>

            <div className="hero-actions">
              <a href="#cursos" className="btn-primary">
                Explorar cursos
              </a>
              <a href="#sobre" className="btn-secondary">
                Conhecer a plataforma
              </a>
            </div>

            <div className="hero-info">
              <div>
                <strong>Do zero</strong>
                <span>ao intermediário</span>
              </div>
              <div>
                <strong>Na prática</strong>
                <span>Aprenda fazendo</span>
              </div>
              <div>
                <strong>No seu ritmo</strong>
                <span>Estude quando quiser</span>
              </div>
            </div>
          </div>

          <div className="hero-visual">
            <div className="visual-glow"></div>

            <div className="code-window">
              <div className="window-header">
                <div className="window-dots">
                  <span></span>
                  <span></span>
                  <span></span>
                </div>
                <span>seu-futuro.java</span>
              </div>

              <div className="code-content">
                <p><span className="code-purple">public class</span> Futuro {'{'}</p>
                <p className="indent"><span className="code-purple">public static void</span> evoluir() {'{'}</p>
                <p className="indent-2"><span className="code-blue">String</span> objetivo = <span className="code-green">"Desenvolvedor"</span>;</p>
                <p className="indent-2"><span className="code-blue">boolean</span> aprender = <span className="code-orange">true</span>;</p>
                <p className="indent-2"><span className="code-purple">while</span> (aprender) {'{'}</p>
                <p className="indent-3">praticar();</p>
                <p className="indent-3">evoluir();</p>
                <p className="indent-2">{'}'}</p>
                <p className="indent">{'}'}</p>
                <p>{'}'}</p>
              </div>

              <div className="code-footer">
                <span className="status-dot"></span>
                Seu próximo capítulo começa agora.
              </div>
            </div>

            <div className="floating-card">
              <span>✦</span>
              <div>
                <strong>Aprendizado contínuo</strong>
                <small>Uma etapa de cada vez.</small>
              </div>
            </div>
          </div>
        </section>

        <section className="courses-section" id="cursos">
          <div className="section-heading">
            <div>
              <span className="eyebrow">SUA JORNADA COMEÇA AQUI</span>
              <h2>Explore nossos cursos</h2>
              <p>Construa sua base e desenvolva novas habilidades.</p>
            </div>
            <span className="course-count">TRILHAS DE APRENDIZADO</span>
          </div>

          <div className="course-grid">
            <article className="course-card">
              <div className="course-icon purple">⌘</div>
              <span className="course-level">INICIANTE</span>
              <h3>Lógica de Programação</h3>
              <p>Desenvolva o raciocínio lógico e aprenda os fundamentos da programação.</p>
              <a href="#cursos">Explorar curso <span>→</span></a>
            </article>

            <article className="course-card">
              <div className="course-icon blue">☕</div>
              <span className="course-level">INICIANTE</span>
              <h3>Java para Iniciantes</h3>
              <p>Conheça a linguagem Java e dê seus primeiros passos no desenvolvimento.</p>
              <a href="#cursos">Explorar curso <span>→</span></a>
            </article>

            <article className="course-card">
              <div className="course-icon green">▤</div>
              <span className="course-level">INICIANTE</span>
              <h3>Banco de Dados e SQL</h3>
              <p>Aprenda a organizar, consultar e manipular dados com SQL.</p>
              <a href="#cursos">Explorar curso <span>→</span></a>
            </article>
          </div>
        </section>

        <section className="about-section" id="sobre">
          <span className="eyebrow">SOBRE A NEXORA</span>
          <h2>Mais do que aprender código.<br />É construir possibilidades.</h2>
          <p>
            Uma plataforma criada para transformar curiosidade em conhecimento
            e conhecimento em projetos reais.
          </p>
        </section>
      </main>

      <footer className="footer">
        <a href="#inicio" className="logo">NEXORA<span>.</span></a>
        <p>Aprenda hoje. Construa o amanhã.</p>
        <span>© 2026 NEXORA</span>

        <div className="api-status">
          {erroApi ? (
            <span>API desconectada</span>
          ) : statusApi ? (
            <span>{statusApi.status}: {statusApi.mensagem}</span>
          ) : (
            <span>Conectando à API...</span>
          )}
        </div>
      </footer>
    </div>
  )
}

export default App
