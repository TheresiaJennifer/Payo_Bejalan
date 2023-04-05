package com.si61.payobejalan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDestinasi extends RecyclerView.Adapter<AdapterDestinasi.ViewHolderDestinasi>{
    private Context ctx;
    private ArrayList arrId, arrNama, arrAlamat, arrJam;

    public AdapterDestinasi(Context ctx, ArrayList arrId, ArrayList arrNama, ArrayList arrAlamat, ArrayList arrJam) {
        this.ctx = ctx;
        this.arrId = arrId;
        this.arrNama = arrNama;
        this.arrAlamat = arrAlamat;
        this.arrJam = arrJam;
    }

    @NonNull
    @Override
    public ViewHolderDestinasi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_item_destinasi, parent, false);//attachtoRoot lazimnya false karena merupakan 2 hal yang terpisah
        return new ViewHolderDestinasi((varView));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDestinasi holder, int position) {

        holder.tvId.setText(arrId.get(position).toString());
        holder.tvNama.setText(arrNama.get(position).toString());
        holder.tvAlamat.setText(arrAlamat.get(position).toString());
        holder.tvJam.setText(arrJam.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return arrNama.size();
    }

    public class ViewHolderDestinasi extends RecyclerView.ViewHolder{
        TextView tvId, tvNama, tvAlamat, tvJam;

        public ViewHolderDestinasi(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvJam = itemView.findViewById(R.id.tv_jam);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Perintah Apa yang Akan Dilakukan?");
                    pesan.setCancelable(true);//jika kita klik dimana saja, maka pesan akan tertutup otomatis. makanya dibuat true

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {//ini sebelah kanan
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String id, nama, alamat, jam;

                            id = tvId.getText().toString();
                            nama = tvNama.getText().toString();
                            alamat = tvAlamat.getText().toString();
                            jam = tvJam.getText().toString();

                            Intent kirim = new Intent(ctx, UbahActivity.class);
                            kirim.putExtra("xId", id);
                            kirim.putExtra("xNama", nama);
                            kirim.putExtra("xAlamat", alamat);
                            kirim.putExtra("xJam", jam);
                            ctx.startActivity(kirim);

                        }
                    });

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyDatabaseHelper myDB = new MyDatabaseHelper(ctx);
                            long eks = myDB.hapusData(tvId.getText().toString());

                            if (eks == -1){
                                Toast.makeText(ctx, "Gagal Hapus Data!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ctx, "Sukses Hapus Data", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();//kalau sdh berhasil, dialog ditutup dan dibalikkan ke MainActivity
                                ((MainActivity) ctx).onResume();//eksekusi on resume, on resume isinya tampil data. jd data yg ditampilkan itu yang terbaru
                            }

                        }
                    });

                    pesan.show();

                    return false;
                }
            });
        }
    }
}
