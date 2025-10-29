#!/bin/bash

# Script para executar o projeto NP2 automaticamente
# Uso: ./run.sh

echo "🚀 Iniciando projeto NP2..."

# Configurar variáveis de ambiente
export DB_URL="jdbc:postgresql://localhost:5432/biblioteca_np2"
export DB_USER="postgres"
export DB_Pass="${DB_Pass:-}"  # Senha vazia por padrão, ou use variável DB_Pass
export GOOGLE_BOOKS_KEY="${GOOGLE_BOOKS_KEY:-}"

echo "📦 Configurando variáveis de ambiente..."
echo "   DB_URL: $DB_URL"
echo "   DB_USER: $DB_USER"
echo "   GOOGLE_BOOKS_KEY: ${GOOGLE_BOOKS_KEY:+configurado}"

# Verificar se o banco de dados existe
echo "🗄️  Verificando banco de dados..."
if PGPASSWORD="$DB_Pass" psql -U postgres -lqt | cut -d \| -f 1 | grep -qw biblioteca_np2; then
    echo "✅ Banco 'biblioteca_np2' existe"
else
    echo "📝 Criando banco de dados 'biblioteca_np2'..."
    PGPASSWORD="$DB_Pass" psql -U postgres -c "CREATE DATABASE biblioteca_np2;" 2>&1
    if [ $? -eq 0 ]; then
        echo "✅ Banco criado com sucesso"
    else
        echo "❌ Erro ao criar banco. Tentando continuar..."
    fi
fi

# Verificar se o PostgreSQL está rodando
echo "🔍 Verificando PostgreSQL..."
if pg_isready -h localhost -p 5432 > /dev/null 2>&1; then
    echo "✅ PostgreSQL está rodando"
else
    echo "❌ PostgreSQL não está rodando. Por favor, inicie o PostgreSQL e tente novamente."
    exit 1
fi

# Verificar se a porta 8085 está em uso
if lsof -Pi :8085 -sTCP:LISTEN -t >/dev/null 2>&1; then
    echo "⚠️  Porta 8085 já está em uso"
    read -p "Deseja parar o processo e iniciar novamente? (s/n): " response
    if [[ "$response" =~ ^[Ss]$ ]]; then
        echo "🛑 Parando processo na porta 8085..."
        lsof -ti:8085 | xargs kill -9 2>/dev/null
        sleep 2
    else
        echo "❌ Processo cancelado"
        exit 1
    fi
fi

# Executar o projeto
echo ""
echo "🎯 Iniciando aplicação Spring Boot..."
echo "📡 A aplicação estará disponível em: http://localhost:8085"
echo "📚 Swagger UI: http://localhost:8085/swagger-ui.html"
echo ""
echo "⏳ Aguarde o startup da aplicação..."
echo ""

./mvnw spring-boot:run

