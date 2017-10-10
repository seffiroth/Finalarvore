package utfpr.dainf.ct.ed.exemplo;

import java.util.LinkedList;
import java.util.Stack;

public class ArvoreBinaria<E> {
    private boolean inicio = true;
    private boolean visitado = false;
    private E dado;
    private ArvoreBinaria<E> esquerda;
    private ArvoreBinaria<E> direita;
    private Stack<ArvoreBinaria<E>> pilha;
    private LinkedList<ArvoreBinaria<E>> fila;
    private ArvoreBinaria<E> ultimoVisitado;

    public static void main(String[] args)
    {
        ArvoreBinaria<Integer> a = new ArvoreBinaria(5);
        a.insereEsquerda(6);
        a.insereEsquerda(8);
        a.insereEsquerda(2);
        a.insereEsquerda(3);
        a.insereEsquerda(4);
        a.insereEsquerda(10);
        
        a.visitaEmOrdem();
        //a.proximoPreOrdem();
    }

    public ArvoreBinaria() {
    }

    public ArvoreBinaria(E dado) {
        this.dado = dado;
    }

    public ArvoreBinaria<E> insereEsquerda(E dado) {
        ArvoreBinaria<E> e = esquerda;
        esquerda = new ArvoreBinaria<>(dado);
        esquerda.esquerda = e;
        return esquerda;
    }

    public ArvoreBinaria<E> insereDireita(E dado) {
        ArvoreBinaria<E> d = direita;
        direita = new ArvoreBinaria<>(dado);
        direita.direita = d;
        return direita;
    }

    protected void visita(ArvoreBinaria<E> no) {
        System.out.print(" " + no.dado);
    }

    public void visitaEmOrdem(ArvoreBinaria<E> raiz) {
        if (raiz != null) {
            ArvoreBinaria.this.visitaEmOrdem(raiz.esquerda);
            visita(raiz);
            ArvoreBinaria.this.visitaEmOrdem(raiz.direita);
        }
    }

    public void visitaEmOrdem() {
        visitaEmOrdem(this);
    }
    
    public void visitaPreOrdem()
    {
        visitaPreOrdem(this);
    }

    public void visitaPreOrdem(ArvoreBinaria<E> a)
    {
        visita(a);
        if (a != null)
        {
            if (a.esquerda != null)
                visitaPreOrdem(a.esquerda);

            if (a.direita != null)
                visitaPreOrdem(a.direita);
        }
    }

    public ArvoreBinaria<E> proximoPreOrdem()
    {
        ArvoreBinaria<E> resultado;
        resultado = null;
     
        if (inicio)
        {
            reinicia();
            pilha.push(this);
            inicio = false;
        }
        
        if (!pilha.isEmpty())
        {
            resultado = pilha.pop();
            
            if (resultado.direita != null)
                pilha.push(resultado.direita);
            if (resultado.esquerda != null)
                pilha.push(resultado.esquerda);
        }

        if (resultado == null)
        {
            inicio = true;
            tiraVisitado(this);
        }

        return resultado;
    }

    public void visitaPosOrdem() { visitaPosOrdem(this); }

    public void visitaPosOrdem(ArvoreBinaria<E> no)
    {
        if (no.esquerda != null)
            visitaPosOrdem(no.esquerda);
        if (no.direita != null)
            visitaPosOrdem(no.direita);
        visita(no);
    }

    public ArvoreBinaria<E> proximoPosOrdem()
    {
        ArvoreBinaria<E> resultado, pai = null;
        resultado = this;

        while (resultado != null)
        {
            if (resultado.visitado == false)
            {
                pai = resultado;
                resultado = resultado.esquerda;
                if (resultado == null && pai != null)
                    resultado = pai.direita;
            }
            else
            {
                if (pai != null)
                   resultado = pai.direita;   
                if (resultado != null && resultado.visitado == true)
                    break;
            }
        }

        resultado = pai;
        
        if (resultado == null)
        {
            inicio = true;
            tiraVisitado(this);
        }

        else
            resultado.visitado = true;

        return resultado;
    }


    private void tiraVisitado(ArvoreBinaria<E> a)
    {
        if (a != null)
        {
            a.visitado = false;
            tiraVisitado(a.esquerda);
            tiraVisitado(a.direita);
        }
    }

    public ArvoreBinaria<E> proximoEmNivel()
    {
        ArvoreBinaria<E> resultado;
        if (fila == null)
            fila = new LinkedList<>();
        if (inicio)
        {
            inicio = false;
            fila.add(this);
        }


        resultado = fila.pollFirst();
        
        if (resultado != null)
        {                
            if (resultado.esquerda != null)
                fila.add(resultado.esquerda);
            if (resultado.direita != null)
                fila.add(resultado.direita);
        }


        if (resultado == null)
        {
            tiraVisitado(this);
            inicio = true;
        }

        return resultado;
    } 



    private void inicializaPilha() {
        if (pilha == null) {
            pilha = new Stack<>();
        }
    }
    
    public void reinicia() {
        inicializaPilha();
        pilha.clear();
        ultimoVisitado = this;
        inicio = true;
    }
    
    public ArvoreBinaria<E> proximoEmOrdem() {
        ArvoreBinaria<E> resultado = null;
        if (inicio) {
            reinicia();
            inicio = false;
        }
        if (!pilha.isEmpty() || ultimoVisitado != null) {
            while (ultimoVisitado != null) {
                pilha.push(ultimoVisitado);
                ultimoVisitado = ultimoVisitado.esquerda;
            }
            ultimoVisitado = pilha.pop();
            resultado = ultimoVisitado;
            ultimoVisitado = ultimoVisitado.direita;
        }
        
        if (resultado == null)
            inicio = true;

        return resultado;
    }
    
    public E getDado() {
        return dado;
    }

    public void setDado(E dado) {
        this.dado = dado;
    }

    protected ArvoreBinaria<E> getEsquerda() {
        return esquerda;
    }

    protected ArvoreBinaria<E> getDireita() {
        return direita;
    }
    
}
