package com.example.electronicjournal.fragments;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.R;
import com.example.electronicjournal.adapters.ExpenseProfitsViewAdapterRV;
import com.example.electronicjournal.database.entity.Expense;
import com.example.electronicjournal.database.entity.Profit;
import com.example.electronicjournal.database.entity.transaction.TransactionForExpenseProfit;
import com.example.electronicjournal.fragments.profit_expanse_add_fragments.AddExpenseFragment;
import com.example.electronicjournal.fragments.profit_expanse_add_fragments.AddProfitFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ViewExpensesProfitsFragment extends Fragment {

    private ExpenseProfitsViewAdapterRV adapterExpensesProfits;
    private RecyclerView recyclerViewExpensesProfits;

    private View view;

    private FloatingActionButton addProfitOrExpenseBTN;
    private FloatingActionButton addExpenseBTN;
    private FloatingActionButton addProfitBTN;

    private Animation openBtns;
    private Animation closeBtns;

    private SearchView searchView;

    private Button changeSortBtn;

    private boolean clicked;

    private AddExpenseFragment addExpenseFragment;
    private AddProfitFragment addProfitFragment;

    private String todayStr;
    private String yesterdayStr;

    private boolean isFirst = true;

    private Comparator<TransactionForExpenseProfit> comparatorNow;

    private String timePattern = App.getInstance().getDateFormat();

    private String textInTextView = "";

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(clicked) clicked = !clicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_expenses_profits_fragment, container, false);

        addProfitOrExpenseBTN = view.findViewById(R.id.add_profit_or_expense_BTN);
        addExpenseBTN = view.findViewById(R.id.add_expense_BTN);
        addProfitBTN = view.findViewById(R.id.add_profit_BTN);

        addProfitBTN.setClickable(false);
        addExpenseBTN.setClickable(false);

        openBtns = AnimationUtils.loadAnimation(view.getContext(), R.anim.open_anim);
        closeBtns = AnimationUtils.loadAnimation(view.getContext(), R.anim.close_anim);

        changeSortBtn = view.findViewById(R.id.sort_BTN);

        comparatorNow = initComparatorTime(true);

        searchView = view.findViewById(R.id.searchViewExpensesProfits);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                initRecyclerView(newText);
                textInTextView = newText;
                return false;
            }
        });

        recyclerViewExpensesProfits = view.findViewById(R.id.view_expenses_profits_RV);
        recyclerViewExpensesProfits.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        changeSortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });


        addProfitOrExpenseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clicked){
                    addExpenseBTN.setVisibility(View.VISIBLE);
                    addProfitBTN.setVisibility(View.VISIBLE);

                    addProfitBTN.setClickable(true);
                    addExpenseBTN.setClickable(true);

                    addExpenseBTN.startAnimation(openBtns);
                    addProfitBTN.startAnimation(openBtns);
                } else {
                    addExpenseBTN.setVisibility(View.INVISIBLE);
                    addProfitBTN.setVisibility(View.INVISIBLE);

                    addProfitBTN.setClickable(false);
                    addExpenseBTN.setClickable(false);

                    addExpenseBTN.startAnimation(closeBtns);
                    addProfitBTN.startAnimation(closeBtns);
                }

                clicked = !clicked;
            }
        });

        addExpenseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpenseFragment = App.getInstance().getAddExpenseFragment();
                addExpenseFragment.show(getParentFragmentManager(), addExpenseFragment.getTag());

                addExpenseBTN.setVisibility(View.INVISIBLE);
                addProfitBTN.setVisibility(View.INVISIBLE);

                addExpenseBTN.startAnimation(closeBtns);
                addProfitBTN.startAnimation(closeBtns);

                clicked = !clicked;
            }
        });

        addProfitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProfitFragment = App.getInstance().getAddProfitFragment();
                addProfitFragment.show(getParentFragmentManager(),addProfitFragment.getTag());

                addExpenseBTN.setVisibility(View.INVISIBLE);
                addProfitBTN.setVisibility(View.INVISIBLE);

                addExpenseBTN.startAnimation(closeBtns);
                addProfitBTN.startAnimation(closeBtns);

                clicked = !clicked;
            }
        });

        if(isFirst) {
            initRecyclerView("");
            isFirst = false;
        }

        App.getInstance()
                .getExpensesDbRepository()
                .getAllExpensesLD()
                .observe(getViewLifecycleOwner(), new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                initRecyclerView("");
            }
        });

        App.getInstance()
                .getProfitsDbRepository()
                .getAllProfitsLD()
                .observe(getViewLifecycleOwner(), new Observer<List<Profit>>() {
            @Override
            public void onChanged(List<Profit> profits) {
                initRecyclerView("");
            }
        });


        return view;
    }


    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.sort_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.sort_by_cheap){
                    changeSortBtn.setText("Сначала недорогие");
                    comparatorNow = initComparatorAmount(false);
                } else if (item.getItemId() == R.id.sort_by_expensive) {
                    comparatorNow = initComparatorAmount(true);
                    changeSortBtn.setText("Сначала дорогие");
                }else if (item.getItemId() == R.id.sort_by_date_new) {
                    changeSortBtn.setText("Сначала новые");
                    comparatorNow = initComparatorTime(true);
                }else if (item.getItemId() == R.id.sort_by_date_late) {
                    changeSortBtn.setText("Сначала старые");
                    comparatorNow = initComparatorTime(false);
                } else {
                    return false;
                }
                initRecyclerView(textInTextView);
                return true;
            }
        });
        popupMenu.show();
    }

    private void initRecyclerView(String s) {
        initTodayYesterday();

        List<Expense> itemsExpenses = App.getInstance()
                .getExpensesDbRepository()
                .getAllExpenses();

        List<Profit> itemsProfits = App.getInstance()
                .getProfitsDbRepository()
                .getAllProfits();


        List<TransactionForExpenseProfit> items = new ArrayList<>();

        for(Expense e: itemsExpenses){
            if(e.getName().toLowerCase().contains(s.toLowerCase())) items.add(e);
        }
        for(Profit p: itemsProfits){
            if(p.getName().toLowerCase().contains(s.toLowerCase())) items.add(p);
        }

        items.sort(comparatorNow);

        adapterExpensesProfits = new ExpenseProfitsViewAdapterRV(items);
        recyclerViewExpensesProfits.setAdapter(adapterExpensesProfits);

        initItemTouchHelper();
    }

    private void initItemTouchHelper(){
        ItemTouchHelper.SimpleCallback swipeToDelete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                TransactionForExpenseProfit ans = adapterExpensesProfits.removeItem(position);
                String text;
                if (ans instanceof Profit) text = "доход";
                else text = "расход";

                Snackbar.make(recyclerViewExpensesProfits, "Вы удалили " + text, Snackbar.LENGTH_LONG)
                        .setAction("Отменить", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(ans instanceof Profit){
                                    App.getInstance().getProfitsDbRepository().addProfit((Profit) ans);
                                } else {
                                    App.getInstance().getExpensesDbRepository().addExpense((Expense) ans);
                                }
                            }
                        }).show();

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.delete))
                        .addActionIcon(R.drawable.trash)
                        .addCornerRadius(1, 20)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDelete);
        itemTouchHelper.attachToRecyclerView(recyclerViewExpensesProfits);
    }

    private void initTodayYesterday(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(timePattern);
        LocalDate today = LocalDate.now();

        todayStr = today.format(dtf);
        yesterdayStr = today.minusDays(1).format(dtf);
    }

    private Comparator<TransactionForExpenseProfit> initComparatorTime(boolean b){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(timePattern);

       return new Comparator<TransactionForExpenseProfit>() {
            @Override
            public int compare(TransactionForExpenseProfit o1, TransactionForExpenseProfit o2) {

                String stringDate1;
                String stringDate2;

                if(o1 instanceof Expense){
                    stringDate1 = (((Expense) o1).getDate());
                } else {
                    stringDate1 = ((Profit) o1).getDate();
                }

                if(o2 instanceof Expense){
                    stringDate2 = ((Expense) o2).getDate();
                } else {
                    stringDate2 = ((Profit) o2).getDate();
                }

                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = sdf.parse(stringDate1);
                    date2 = sdf.parse(stringDate2);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                assert date1 != null;

                if(b) {
                    if (date1.compareTo(date2) > 0) {
                        return -1;
                    } else if (date1.compareTo(date2) < 0) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    if (date1.compareTo(date2) < 0) {
                        return -1;
                    } else if (date1.compareTo(date2) > 0) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        };
    }


    private Comparator<TransactionForExpenseProfit> initComparatorAmount(boolean b){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(timePattern);

        return new Comparator<TransactionForExpenseProfit>() {
            @Override
            public int compare(TransactionForExpenseProfit o1, TransactionForExpenseProfit o2) {

               int amount1;
               int amount2;

                if(o1 instanceof Expense){
                    amount1 = ((Expense) o1).getAmount();
                } else {
                    amount1 = ((Profit) o1).getAmount();
                }

                if(o2 instanceof Expense){
                    amount2 = ((Expense) o2).getAmount();
                } else {
                    amount2 = ((Profit) o2).getAmount();
                }


                if(b) {
                    if (amount1 > amount2) {
                        return -1;
                    } else if (amount1 < amount2) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    if (amount1 < amount2) {
                        return -1;
                    } else if (amount1 > amount2) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        };
    }

}