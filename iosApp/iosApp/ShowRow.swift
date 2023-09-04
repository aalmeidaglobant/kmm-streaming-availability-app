//
//  ShowRow.swift
//  iosApp
//
//  Created by Akme Re Monteiro De Almeida on 03/09/23.
//  Copyright © 2023 orgName. All rights reserved.
//
import SwiftUI
import shared

struct ShowRow: View {
    var show: Show

        var body: some View {
            HStack() {
                VStack(alignment: .leading, spacing: 10.0) {
                    Text("Show title: \(show.title)")
                }
                Spacer()
            }
        }
}


struct ShowRow_Previews: PreviewProvider {
    static var previews: some View {
        ShowRow(show: Show.Movie( title: "Batma"))
        
    }
}
