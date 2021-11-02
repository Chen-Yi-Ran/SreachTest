package com.example.sreachtest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sreachtest.bean.Ticket;
//真正把图片和文字显示出来
public class TicketDetailActivity extends AppCompatActivity {

    public static final String TICKET = "TICKET";

    private ImageView imageView;
    private TextView textView;
    private Ticket mTicket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
        initData();
        initElement();
        init();
    }

    private void init() {
        int img =mTicket.getImg() ;
        String name=mTicket.getName();
       /* String ticketNum = "余票"+mTicket.getTicketNum()+"张";
        departureTimeTV.setText(mTicket.getDepartureTime());
        originStationTV.setText(mTicket.getOriginStation());
        destinationStationTV.setText(mTicket.getDestinationStation());
        busTypeTV.setText(mTicket.getBusType());
        ticketPriceTV.setText(ticketPrice);
        ticketNumTV.setText(ticketNum);*/
        imageView.setImageResource(img);
        textView.setText(name);
    }

    private void initElement() {
        imageView=(ImageView) findViewById(R.id.item_image);
        textView=(TextView) findViewById(R.id.item_name);
       /* destinationStationTV = findViewById(R.id.destination_station_tv);
        busTypeTV = findViewById(R.id.bus_type_tv);
        ticketPriceTV = findViewById(R.id.ticket_price_tv);
        ticketNumTV = findViewById(R.id.ticket_num_tv);*/
    }

    private void initData() {
        mTicket = (Ticket) getIntent().getSerializableExtra(TICKET);
    }
}