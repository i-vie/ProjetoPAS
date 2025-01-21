<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" contents="width=device-width, initial scale=1.0">
    <title>Gourmet Manager</title>
    <link rel="stylesheet" href="estilos.css">
    <link rel="stylesheet" href="https://use.typekit.net/opk3aod.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
        href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
        rel="stylesheet">
        <link
        href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
        rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</head>

<body>

    <?php
        $page = 'about'; //Nome da página atual
        include 'navbar.php';
    ?>

    <main>
        <div class="title">
            <h1>Sobre nós</h1>
        </div>

        <!-- Foram usados flip-cards (baseado no apresentado em W3Schools) para apresentar várias secções da página -->
         <!-- As flip-cards estão incluidas num grid system para que a página seja responsiva e para uma organização mais fácil-->
         <div class="container">
            <div class="row justify-content-around">
                <div class="col-lg-6 d-flex justify-content-center">
                    <div class="flip-card">
                        <div class="flip-card-inner">
                            <div class="flip-card-front">
                                <h2>A aplicação</h2>
                            </div>
                            <div class="flip-card-back">
                                <p>O Gourmet Manager é um sistema moderno de gestão de restaurantes, que torna a vida mais fácil para qualquer 
                                    empreendedor da área da restauração. Este sistema permite a criação, consulta e eliminação de 
                                    dados relativos à getsão de pedidos e reservas.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <br>
                <div class="col-lg-6 d-flex justify-content-center">
                    <div class="flip-card">
                        <div class="flip-card-inner">
                            <div class="flip-card-front">
                                <h2>O website</h2>
                            </div>
                            <div class="flip-card-back">
                                <p>O nosso Website pretende ser um espaço de divulgação e apresentação da nossa aplicação  móvel. 
                                    Através do menu de navegação é possível aceder a uma
                                    descrição sobre nós e o nosso projeto, às funcionalidades da nossa aplicação, a perguntas 
                                    frequentes e a uma explicação sobre o funcionamento da aplicação e a um formulário de contacto.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="row justify-content-evenly">
                <div class="col-lg-6 d-flex justify-content-center">
                    <div class="flip-card">
                        <div class="flip-card-inner">
                            <div class="flip-card-front">
                                <h2>O logótipo</h2>
                            </div>
                            <div class="flip-card-back">
                                <p>
                                    O nosso logótipo, composto pelo símbolo e pelo nome da aplicação foi imaginado como forma de 
                                    junção de símbolos tipicamente associados à restauração (o prato, o garfo e a colher), mas com 
                                    um toque pessoal e moderno. Optámos por um símbolo de forma redonda para que seja mais facilmente
                                    adaptado a diferentes ambientes, como, por exemplo, no símbolo da aplicação.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <br>
                <div class="col-lg-6 d-flex justify-content-center">
                    <div class="flip-card">
                        <div class="flip-card-inner">
                            <div class="flip-card-front">
                                <h2>As cores</h2>
                            </div>
                            <div class="flip-card-back">
                                <p>O azul escuro é associado a confiança e a profissionalismo que são dois valores que pretendemos transmitir 
                                    aos nossos utilizadores. O amarelo transmite dinamismo e energia e proporciona um contraste 
                                    marcante com o azul, sendo usado em lugares de destaque. O branco e o cinzento, como cores neutras e 
                                    associadas à simplicidade foram escolhidas de forma a poder destacar e ligar as restantes cores, permitindo 
                                    contraste e legibilidade e evitando o sobrecarregamento das cores amarela e azul.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-lg-6 d-flex justify-content-center">
                    <div class="flip-card">
                        <div class="flip-card-inner">
                            <div class="flip-card-front">
                                <h2>Os ícones</h2>
                            </div>
                            <div class="flip-card-back">
                                <p>
                                    Os ícones elaborados para este projeto estão associados à área da restauração. Criamos ícones simples para 
                                    manter uma boa legibilidade, mas que fossem facilmente associados ao que representam. Decidimos incluir 
                                    alguns elementos de ligação com o logótipo em alguns ícones (os talheres são um elemento em comum na maioria 
                                    dos ícones criados). A cor branca assegura a legibilidade tanto em fundo azul como em fundo amarelo e 
                                    pareceu-nos a melhor escolha.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <br>
                <div class="col-lg-6 d-flex justify-content-center">
                    <div class="flip-card">
                        <div class="flip-card-inner">
                            <div class="flip-card-front">
                                <h2>Tipografia</h2>
                            </div>
                            <div class="flip-card-back">
                                <p>
                                    A fonte Ofelia Display Bold, presente no logótipo, possui um design 
                                    robusto e consegue criar um impacto visual forte. Esta escolha é justificada pela necessidade 
                                    de uma fonte que pudesse ser marcante e elegante, tornando-se facilmente reconhecida e conferindo 
                                    personalidade à nossa marca.
                                    As fontes Poppins Regular, Medium e Semibold para a aplicação e website garantem uma boa legibilidade e transmitem uma imagem moderna, sofisticada e consistente 
                                    em todo o site e aplicação.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <br>
            <br>
            <div class="description">

                <p>A aplicação Gourmet Manager foi elaborada pelos alunos André Umba e Iolanda Vieira do CTESP em
                    Tecnologias
                    Web e
                    Dispositivos Móveis no âmbito da displina Programação Aplicada do Lado do Cliente.</p>
            </div>

        </div>
    </main>

        

    <!-- Incluir o footer -->
    <?php include 'footer.php';?>

    <script src="codigo.js"></script>


</body>

</html>