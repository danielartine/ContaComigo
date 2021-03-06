package com.devmob.contacomigov2.adapters.expandableLists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.devmob.contacomigov2.R;
import com.devmob.contacomigov2.fragments.ItemFragmento;
import com.devmob.contacomigov2.model.Pessoa;
import com.devmob.contacomigov2.model.Produto;
import com.devmob.contacomigov2.model.ProdutoConsumido;

import java.util.ArrayList;
import java.util.List;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by DevMachine on 29/03/2017.
 */

// GROUP EH O PRODUTO, CHILD EH PESSOA

public class ProdutoExpandableListAdapter extends BaseExpandableListAdapter {

    Locale defaultLocale = Locale.getDefault();
    Currency currency = Currency.getInstance(defaultLocale);

    private static final String TAG = "ProdutoExpandListAdap";


    private Context context;
    private ArrayList<Produto> prodList;

    public ProdutoExpandableListAdapter(Context context, ArrayList<Produto> prodList) {
        this.context = context;
        this.prodList = prodList;
    }

    @Override
    public Object getChild(int indiceProduto, int indicePessoa) {
        List<Pessoa> consumidores = prodList.get(indiceProduto).getConsumidores();
        return consumidores.get(indicePessoa);
    }


    @Override
    public long getChildId(int indiceProduto, int indicePessoa) {
        return indicePessoa;
    }


    @Override
    public View getChildView(int indiceProduto, int indicePessoa, boolean isLastChild,
                             View view, ViewGroup parent) {

        Pessoa detailInfo = (Pessoa) getChild(indiceProduto, indicePessoa);
        Produto produto = (Produto) getGroup(indiceProduto);

        double price = 0;
        int quantidade = 1;
        for (ProdutoConsumido pc : detailInfo.getProdutosConsumidos()) {
            if(pc.getProduto().getId() == produto.getId()) {
                price = pc.getPrecoPago();
                quantidade = pc.getQuantidade();
            }
        }


        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_de_produto_item_pessoa, null);
        }

        TextView pessoa = (TextView) view.findViewById(R.id.pessoa);
        pessoa.setText((detailInfo.getNome() + " x " + quantidade).trim());
        TextView pessoaPreco = (TextView) view.findViewById(R.id.pessoaPreco);
        if (ItemFragmento.gorjeta.getAtivo() == false)
            pessoaPreco.setText(String.format(currency.getSymbol()+" %.2f", price));
        else
            pessoaPreco.setText(String.format(currency.getSymbol()+" %.2f", price*ItemFragmento.gorjeta.getValor()));

        return view;
    }

    @Override
    public int getChildrenCount(int indiceProduto) {
        return prodList.get(indiceProduto).getConsumidores().size();
    }

    @Override
    public Object getGroup(int indiceProduto) {
        return prodList.get(indiceProduto);
    }

    @Override
    public int getGroupCount() {
        return prodList.size();
    }

    @Override
    public long getGroupId(int indiceProduto) {
        return indiceProduto;
    }

    @Override
    public View getGroupView(int indiceProduto, boolean isLastChild, View view,
                             ViewGroup parent) {

        Produto produto = (Produto) getGroup(indiceProduto);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.list_de_produto_item_produto, null);
        }
        //TODO adicionar contador de quantidade do lado do nome do produto
        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText((produto.getNome() + " x " + produto.getQuantidade()).trim());
        TextView productPrice = (TextView) view.findViewById(R.id.productPrice);
        productPrice.setText(String.format(currency.getSymbol()+" %.2f",produto.getPreco() * produto.getQuantidade()));
        if (ItemFragmento.gorjeta.getAtivo() == false)
            productPrice.setText(String.format(currency.getSymbol()+" %.2f",produto.getPreco()* produto.getQuantidade()));
        else
            productPrice.setText(String.format(currency.getSymbol()+" %.2f",produto.getPreco()* produto.getQuantidade()*ItemFragmento.gorjeta.getValor()));

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int indiceProduto, int indicePessoa) {
        return true;
    }

    public void resetaLista(List<Produto> produtos) {
        this.prodList = new ArrayList<>(produtos);
    }
}